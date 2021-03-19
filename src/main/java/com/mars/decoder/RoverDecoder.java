package com.mars.decoder;

import java.util.Arrays;
import java.util.List;

import static com.mars.decoder.DecoderUtil.INVALID_COMMAND;
import static java.lang.Character.*;

public class RoverDecoder {
    public static final String INVALID_ORIENTATION_CODE = "Invalid orientation code, choose from E, W, S, N.";

    public static String[] decodeRoverCreate(String command) throws Exception {
        validateRoverCreate(command);

        return command.split(" ");
    }

    public static String[] decodeRoverMove(String command) throws Exception {
        validateRoverMove(command);

        return command.split(" ");
    }

    public static String[] decodeGetDirections(String command) throws Exception {
        return command.split(" ");
    }

    private static void validateRoverCreate(String command) throws Exception {
        if (command == null) {
            throw new Exception(INVALID_COMMAND);
        }

        String[] splitCommand = command.split(" ");

        validateOrientation(splitCommand[2].charAt(0));
    }

    private static void validateOrientation(Character o) throws Exception {
        Character[] validCodes = {'N', 'W', 'S', 'E'};
        List<Character> validCodesList = Arrays.asList(validCodes);
        if (!validCodesList.contains(o)) {
            throw new Exception(INVALID_ORIENTATION_CODE);
        }
    }

    private static void validateRoverMove(String command) throws Exception {
        if (command == null || !isDigit(command.charAt(0))) {
            throw new Exception(INVALID_COMMAND);
        }
        String movementPart = command.substring(2);
        if (movementPart.length() < 1 ||
            !movementPart.contains("M") ||
            containsBadChars(movementPart)) {
                throw new Exception(INVALID_COMMAND);
        }
    }

    private static boolean containsBadChars(String movementPart) {
        for (int i = 0; i < movementPart.length(); i++){
            char c = movementPart.charAt(i);
            if (c != 'M' && c != 'L' && c != 'R') {
                return true;
            }
        }
        return false;
    }
}
