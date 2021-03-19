package com.mars.service;

import com.mars.enums.Orientation;
import com.mars.model.Plateau;
import com.mars.model.PlateauField;
import com.mars.model.Rover;
import com.mars.singleton.Mars;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static com.mars.enums.Orientation.*;
import static java.lang.Integer.parseInt;

@Service
public class RoverServiceImpl implements RoverService {
    public static final String OCCUPIED_POSITION = "There is already rover on that position!";
    public static final String END_OF_PLATEAU = "Rover cannot move, he reached end of plateau";
    static Mars mars = Mars.getInstance();

    private final PlateauServiceImpl plateauService;

    public RoverServiceImpl(PlateauServiceImpl plateauService) {
        this.plateauService = plateauService;
    }

    @Override
    public Rover createRover(String[] command) throws Exception {
        List<Rover> rovers = mars.getRovers();
        Rover rover = new Rover(parseInt(command[0]), parseInt(command[1]), rovers.size(), getOrientationFromCode(command[2]));

        //if there is already rover on that position, exception should be thrown
        if (plateauService.readPlateauField(parseInt(command[0]), parseInt(command[1])).getRover() != null) {
            throw new Exception(OCCUPIED_POSITION);
        }

        rovers.add(rover);
        mars.setRovers(rovers);
        PlateauField plateauField = plateauService.updatePlateauField(parseInt(command[0]), parseInt(command[1]), rover);
        return plateauField.getRover();
    }

    @Override
    public Rover moveRover(String[] command) throws Exception {
        List<Rover> rovers = mars.getRovers();
        Rover rover = rovers.stream().filter(r -> r.getId() == parseInt(command[0])).collect(Collectors.toList()).get(0);

        String movementCommand = command[1];
        executeMoveRover(rover, movementCommand);

        return rover;
    }

    @Override
    public String getDirections(String[] command) {
        List<Rover> rovers = mars.getRovers();
        Rover rover = rovers.get(parseInt(command[0]));

        Integer startX = rover.getX(), startY = rover.getY();
        Integer destinationX = parseInt(command[1]), destinationY = parseInt(command[2]);

        PlateauField destination = new PlateauField(destinationX, destinationY, null, true);

        Map<String, PlateauField> path = findPath(new PlateauField(startX, startY, null), new PlateauField(destinationX, destinationY, null),
                mars.getPlateau().getPlateau());

        List<PlateauField> fieldsList = new ArrayList<>();

        PlateauField temp = path.get(destination.getX() + "," + destination.getY());
        fieldsList.add(temp);

        while(fieldsList.get(fieldsList.size() - 1) != null) {
            fieldsList.add(path.get(fieldsList.get(fieldsList.size() - 1).getX() + "," + fieldsList.get(fieldsList.size() - 1).getY()));
        }


        //return generateStringMatrix(startX, startY, destinationX, destinationY, fieldsList);

        return generateCommandString(startX, startY, destinationX, destinationY, fieldsList, rover);
    }

    @Override
    public String printRoute(String[] command) throws Exception {
        List<Rover> rovers = mars.getRovers();
        Rover rover = rovers.get(parseInt(command[0]));

        Integer startX = rover.getX(), startY = rover.getY();
        Integer destinationX = parseInt(command[1]), destinationY = parseInt(command[2]);

        PlateauField destination = new PlateauField(destinationX, destinationY, null, true);

        Map<String, PlateauField> path = findPath(new PlateauField(startX, startY, null), new PlateauField(destinationX, destinationY, null),
                mars.getPlateau().getPlateau());

        List<PlateauField> fieldsList = new ArrayList<>();

        PlateauField temp = path.get(destination.getX() + "," + destination.getY());
        fieldsList.add(temp);

        while(fieldsList.get(fieldsList.size() - 1) != null) {
            fieldsList.add(path.get(fieldsList.get(fieldsList.size() - 1).getX() + "," + fieldsList.get(fieldsList.size() - 1).getY()));
        }


        return generateStringMatrix(startX, startY, destinationX, destinationY, fieldsList);
    }

    public ArrayList<PlateauField> getNeighbours(PlateauField field, PlateauField[][] plateau) {
        ArrayList<PlateauField> neighbours = new ArrayList<>();

        int R = mars.getHeight();
        int C = mars.getWidth();

        int[] dr = new int[]{-1, +1, 0, 0};
        int[] dc = new int[]{0, 0, +1, -1};

        for (int i=0; i<4; i++) {
            int rr = field.getX() + dr[i];
            int cc = field.getY() + dc[i];

            if (rr < 0 || cc < 0) continue;
            if (rr >= R || cc >= C) continue;

            if (plateau[rr][cc].isVisited()) continue;

            PlateauField neighbour = plateau[rr][cc];
            neighbours.add(neighbour);
        }

        for (Rover r : mars.getRovers()) {
            PlateauField f = new PlateauField(r.getX(), r.getY(), null);
            neighbours.remove(f);
        }

        return neighbours;
    }

    public Map<String, PlateauField> findPath(PlateauField start, PlateauField end, PlateauField[][] plateau) {
        Queue<PlateauField> queue = new LinkedList<>();
        List<PlateauField> path = new ArrayList<>();

        queue.add(start);

        Plateau tempPlateau = mars.getPlateau();
        setAllNotVisited(tempPlateau.getPlateau());
        tempPlateau.getPlateau()[start.getX()][start.getY()].setVisited(true);
        mars.setPlateau(tempPlateau);

        Map<String, PlateauField> parentMap = new HashMap<>();

        PlateauField field = start;
        parentMap.put(start.getX() + "," + start.getY(), null);
        while (!queue.isEmpty()) {
            field = queue.remove();
            path.add(field);

            if (field.getY().equals(end.getY()) &&
                field.getX().equals(end.getX())) break;

            ArrayList<PlateauField> adjNodes = getNeighbours(field, plateau);
            for (PlateauField f : adjNodes) {
                if (!(mars.getPlateau().getPlateau()[f.getX()][f.getY()].isVisited())) {
                    queue.add(f);
                    parentMap.put(f.getX() + "," + f.getY(), field);
                    Plateau tPlateau = mars.getPlateau();
                    tPlateau.getPlateau()[f.getX()][f.getY()].setVisited(true);
                    mars.setPlateau(tPlateau);
                }
            }
        }

        return parentMap;
    }

    private String generateCommandString(Integer startX, Integer startY, Integer destinationX, Integer destinationY,
                                         List<PlateauField> fieldsList, Rover rover) {
        StringBuilder output = new StringBuilder();

        for (int i = fieldsList.size() - 1; i > 0; i--) {
            String rotation = getRotation(fieldsList.get(i), fieldsList.get(i-1), rover.getOrientation(), rover);
            output.append(rotation);
        }
        output.append("M");
        return output.toString();
    }

    private String getRotation(PlateauField first, PlateauField second, Orientation orientation, Rover rover) {
        if (first != null) {
            if (first.getX() > second.getX()) {
                return countRotations(orientation, "UP", rover);
            } else if (first.getX() < second.getX()) {
                return countRotations(orientation, "DOWN", rover);
            }

            if (first.getY() > second.getY()) {
                return countRotations(orientation, "LEFT", rover);
            } else if (first.getY() < second.getY()) {
                return countRotations(orientation, "RIGHT", rover);
            }
        }

        return "";
    }

    private String countRotations(Orientation o, String direction, Rover rover) {
        switch (o) {
            case EAST:
                if (direction.equals("UP")) {
                    String output = "LM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("DOWN")) {
                    String output = "RM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'R'));
                    return output;
                }
                if (direction.equals("LEFT")) {
                    String output = "LLM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("RIGHT")) {
                    return "M";
                }
            case WEST:
                if (direction.equals("UP")) {
                    String output = "RM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'R'));
                    return output;
                }
                if (direction.equals("DOWN")) {
                    String output = "LM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("LEFT")) {
                    return "M";
                }
                if (direction.equals("RIGHT")) {
                    String output = "LLM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
            case NORTH:
                if (direction.equals("UP")) {
                    return "M";
                }
                if (direction.equals("DOWN")) {
                    String output = "LLM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("LEFT")) {
                    String output = "LM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("RIGHT")) {
                    String output = "RM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'R'));
                    return output;
                }
            case SOUTH:
                if (direction.equals("UP")) {
                    String output = "LLM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
                if (direction.equals("DOWN")) {
                    return "M";
                }
                if (direction.equals("LEFT")) {
                    String output = "RM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'R'));
                    return output;
                }
                if (direction.equals("RIGHT")) {
                    String output = "LM";
                    rover.setOrientation(countNewOrientation(rover.getOrientation(), 'L'));
                    return output;
                }
            default: return "";
        }
    }

    private void setAllNotVisited(PlateauField[][] plateau) {
        for (int i = 0; i < mars.getHeight(); i++) {
            for (int j = 0; j < mars.getWidth(); j++) {
                plateau[i][j] = new PlateauField(i, j, null);
                plateau[i][j].setVisited(false);
            }
        }
    }

    private String generateStringMatrix(Integer startX, Integer startY, Integer destinationX, Integer destinationY,
                                            List<PlateauField> fieldsList) {
        String[][] stringMatrix = new String[mars.getHeight()][mars.getWidth()];

        PlateauField[][] plateau = mars.getPlateau().getPlateau();

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < mars.getHeight(); i++) {
            for (int j = 0; j < mars.getWidth(); j++) {
                if (plateau[i][j].getRover() != null) {
                    stringMatrix[i][j] = "[#]";
                } else {
                    stringMatrix[i][j] = "[-]";
                }
            }
        }


        fieldsList.remove(null);

        for (PlateauField field : fieldsList) {
            stringMatrix[field.getX()][field.getY()] = "[*]";
        }

        stringMatrix[startX][startY] = "[S]";
        stringMatrix[destinationX][destinationY] = "[D]";

        for (int i = 0; i < mars.getHeight(); i++) {
            for (int j = 0; j < mars.getWidth(); j++) {
                output.append(stringMatrix[i][j]);
            }
            output.append("\n");
        }

        return output.toString();
    }

    private void executeMoveRover(Rover rover, String movementCommand) throws Exception {
        for (int i = 0; i < movementCommand.length(); i++){
            char c = movementCommand.charAt(i);
            if (c == 'L' || c == 'R') {
                Orientation newOrientation = countNewOrientation(rover.getOrientation(), c);
                rover.setOrientation(newOrientation);
            } else if (c == 'M') {
                doMove(rover);
            }
        }
    }

    private void doMove(Rover rover) throws Exception {
        Integer x = rover.getX();
        Integer y = rover.getY();
        PlateauField oldPlateauField = plateauService.readPlateauField(x, y);
        Orientation o = rover.getOrientation();
        switch (o) {
            case EAST:
                y = y + 1; break;
            case WEST:
                y = y - 1; break;
            case NORTH:
                x = x - 1; break;
            case SOUTH:
                x = x + 1; break;
        }
        PlateauField newPlateauField = plateauService.readPlateauField(x, y);
        if (newPlateauField == null) {
            throw new Exception(END_OF_PLATEAU);
        } else if (newPlateauField.getRover() != null) {
            throw new Exception(OCCUPIED_POSITION);
        } else {
            oldPlateauField.setRover(null);
            rover.setX(x);
            rover.setY(y);
            newPlateauField.setRover(rover);
        }
        Plateau plateau = mars.getPlateau();
        plateau.getPlateau()[oldPlateauField.getX()][oldPlateauField.getY()] = oldPlateauField;
        plateau.getPlateau()[x][y] = newPlateauField;
        mars.setPlateau(plateau);
    }

    private Orientation getOrientationFromCode(String code) {
        switch (code) {
            case "E":
                return EAST;
            case "W":
                return WEST;
            case "N":
                return NORTH;
            case "S":
                return SOUTH;
            default: return null;
        }
    }


    private Orientation countNewOrientation(Orientation o, Character c) {
        switch (c) {
            case 'L':
                switch (o) {
                    case EAST:
                        return NORTH;
                    case NORTH:
                        return WEST;
                    case WEST:
                        return SOUTH;
                    case SOUTH:
                        return EAST;
                }
            case 'R':
                switch (o) {
                    case EAST:
                        return SOUTH;
                    case SOUTH:
                        return WEST;
                    case WEST:
                        return NORTH;
                    case NORTH:
                        return EAST;
                }
            default: return null;
        }
    }
}
