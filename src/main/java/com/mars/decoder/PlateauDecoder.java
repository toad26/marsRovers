package com.mars.decoder;

import static com.mars.decoder.DecoderUtil.INVALID_COMMAND;
import static java.lang.Character.*;
import static java.lang.Integer.parseInt;

public class PlateauDecoder {
    public static final int MAX_PLATEAU_DIMENSION = 1000;
    public static final String PLATEAU_DIMENSIONS_EXCEPTION = "Plateau dimensions must be higher than 0 and maximum " + MAX_PLATEAU_DIMENSION + ".";


    public static Integer[] decodePlateauCreate(String command) throws Exception {
        validatePlateauCreate(command);

        String[] splitCommand = command.split(" ");
        Integer[] integerCommands = new Integer[2];
        integerCommands[0] = parseInt(splitCommand[0]);
        integerCommands[1] = parseInt(splitCommand[1]);
        return integerCommands;
    }

    public static Integer[] decodePlateauFieldRead(String command) throws Exception {
        validatePlateauFieldRead(command);

        String[] splitCommand = command.split(" ");
        Integer[] integerCommands = new Integer[2];
        integerCommands[0] = parseInt(splitCommand[0]);
        integerCommands[1] = parseInt(splitCommand[1]);
        return integerCommands;
    }

    private static void validatePlateauCreate(String command) throws Exception {
        if (command == null) {
            throw new Exception(INVALID_COMMAND);
        }

        String[] splitCommand = command.split(" ");

        if (parseInt(splitCommand[0]) <= 0 || parseInt(splitCommand[0]) > MAX_PLATEAU_DIMENSION
                || parseInt(splitCommand[1]) <= 0 || parseInt(splitCommand[1]) > MAX_PLATEAU_DIMENSION)  {
            throw new Exception(PLATEAU_DIMENSIONS_EXCEPTION);
        }
    }

    private static void validatePlateauFieldRead(String command) throws Exception {
        if (command == null || command.length() != 3||
                !isDigit(command.charAt(0)) ||
                !isSpaceChar(command.charAt(1)) ||
                !isDigit(command.charAt(2))) {
            throw new Exception(INVALID_COMMAND);
        }
    }
}
