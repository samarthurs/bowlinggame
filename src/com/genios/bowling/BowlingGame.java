package com.genios.bowling;

import java.util.Arrays;
import java.util.Scanner;

public class BowlingGame {
    
    public int score() {
        int score = 0;
        Scanner scanner = new Scanner(System.in);

        boolean isStrike = false;
        boolean isSpare = false;
        int[] framesScore = new int[11];

        for (int frame = 1; frame < 11; frame++) { // there are 10 frames in a Bowling Game
            // in each frame, the player can bowl 2 rolls max. Only 1 roll if the first roll in a frame is a strike
            int pinsDownPerFrame = 0;
            for (int chance = 1; chance < 3; chance++) { // max 2 chances for a player to roll the balls per Frame

                System.out.println("Frame : " + frame + " -- Roll : " + chance);
                if (chance == 2) System.out.println("Remaining pins to knock down : " + (10 - pinsDownPerFrame));
                System.out.println("Please roll the ball...");
                int currentPinsDown = scanner.nextInt(); // this roll should not give more than 10 for the sum of two rolls. Number of pins down is actually the input from the Front end

                // Validation for the number of pinsDown to be entered
                // Cannot enter more than 10 at any roll 1 or 2. Cannot enter more (10 - pinsDownRoll1)
                currentPinsDown = validatePinsDown(scanner, currentPinsDown);

                pinsDownPerFrame += currentPinsDown; // Add the pinsDown in each roll

                System.out.println("No. of Pins down : " + currentPinsDown);

                // Check for a strike X
                if (!isStrike) {
                    if (currentPinsDown == 10) {
                        isStrike = true;
                        System.out.println("It is a Strike !!!X!!! : Frame : " + frame);
                        if(isSpare && chance == 1){ // A Strike after a Spare. spare --> strike
                            framesScore[frame - 1] += currentPinsDown;
                            System.out.println("Score for Frame " + (frame - 1) + " : <><><><>" + (10 + currentPinsDown) + "<><><><>");
                        }
                        break; // there is no need to roll the ball for the 2nd time in the current frame as it is a Strike. So break the inner loop
                    }
                }

                // Check for a spare /
                if (pinsDownPerFrame == 10) {  // count of the pinsDown in two rolls
                    if (currentPinsDown != 10) { // if currentPinsDown = 10 and chance = 1 then it a strike
                        isSpare = true;
                        System.out.println("It is a spare /");
                    } else {
                        System.out.println("It is a Strike !!!X!!! : Frame : " + frame);
                    }
                }

                if (isStrike) {
                    if (chance == 1 && currentPinsDown == 10) { // Consecutive strikes.. prev->prev should be taken care for Consecutive strikes
                        //countStrikeScore(framesScore, frame, currentPinsDown); // this is for Consecutive strikes
                        if (frame > 2) { // if frame is greater than 2, i.,e for example 3, then add currentPinsDown to framesScore[2] and framesScore[1]
                            framesScore[frame - 1] += currentPinsDown; //add the points to previous
                            //if (framesScore[frame - 2] > 10) {// add to the previous of previous only if prev->prev was a strike. frameScore will be >=10 if it was a previous strike/spare
                            if (!isSpare && framesScore[frame - 2] > 10) {// spare -> strike -> strike. Only if prev->prev( was a Strike)
                                framesScore[frame - 2] += currentPinsDown; // Add to the previous of previous only if prev->prev was a strike. frameScore will be >=10 if it was a previous strike/spare
                                isSpare = false; // spare -> strike -> strike
                            }
                        } else {
                            framesScore[frame - 1] += currentPinsDown;
                        }

                        break; // break because it is a consecutive strike. If it is the first chance and strike, then break
                    } else if (chance == 1 && currentPinsDown < 10) { // after two previous strikes, it is a normal roll i.e., currentPinsDown<10
                        countStrikeScore(framesScore, frame, currentPinsDown); // it is not a consecutive strike. Only prev was a strike. prev->prev was not
                        if(isSpare)isSpare=false;

                    }

                    // if it was a strike in the previous frame, then the pinsDown for the next 2 rolls will be added to the previous strike
                    if (chance == 2) {
                        framesScore[frame - 1] += currentPinsDown;
                        System.out.println("Score for Frame " + (frame - 1) + " : -----)" + (10 + pinsDownPerFrame) + "(-----");
                        isStrike = false;
                    }
                }

                if (isSpare) {
                    if (chance == 1) { // if it was a spare in the previous frame, then the score will be sum of 10 and pinsDown in the first roll of the next Frame
                        framesScore[frame - 1] += currentPinsDown;
                        System.out.println("Score for Frame " + (frame - 1) + " : <><><><>" + (10 + currentPinsDown) + "<><><><>");
                        isSpare = false;
                    }
                }
            }// end of 1 or 2 rolls in per Frame

            if (frame == 10 && isStrike) { // Strike on the last frame will earn 2 more rolls
                System.out.println("Strike on the Last Frame... So roll twice");

                System.out.println("Additional Role 1. Please roll...");
                int strikeBonusRoll1 = scanner.nextInt();
                framesScore[frame] += strikeBonusRoll1;
                framesScore[frame - 1] += strikeBonusRoll1; // if it is a strike in 9 and 10 and then, frameScore[9] will be 10 + 10 + strikeRollBonus1

                System.out.println("Additional Role 2. Last roll of the Game. Please Roll...");
                int strikeBonusRoll2 = scanner.nextInt();
                framesScore[frame] += strikeBonusRoll2;

            } else if (frame == 10 && isSpare) { //Spare on the last frame will earn 1 more roll
                System.out.println("Spare on the Last Frame... So roll once more");
                int spareBonusRoll = scanner.nextInt();
                framesScore[frame] += spareBonusRoll;
            }

            framesScore[frame] += pinsDownPerFrame;
            Arrays.stream(framesScore).skip(1).forEach(eachScore -> System.out.print(eachScore + " || "));
            System.out.println("Score at the end of the frame : " + frame + " is : --->>>" + Arrays.stream(framesScore).sum() + "<<<---");
            System.out.println("###############################################################################");
        }// end of 10 Frames. End of the Game
        score = Arrays.stream(framesScore).sum();
        return score;
    }

    private int validatePinsDown(Scanner scanner, int currentPinsDown) {
        if (currentPinsDown > 10) {
            System.out.println("Cannot knock more than " + currentPinsDown + " pins at once!!!");
            System.out.println("Please Re enter the pins to be knocked down");
            currentPinsDown = scanner.nextInt();
        }
        return currentPinsDown;
    }

    private void countStrikeScore(int[] framesScore, int frame, int currentPinsDown) {
        if (frame > 2) { // if frame is greater than 2, i.,e for example 3, then add currentPinsDown to framesScore[2] and framesScore[1]
            framesScore[frame - 1] += currentPinsDown; //add the points to previous
            if (framesScore[frame - 2] >= 10) {// add to the previous of previous only if prev->prev was a strike. frameScore will be >=10 if it was a previous strike/spare
                framesScore[frame - 2] += currentPinsDown; //add the points to previous of previous
            }
        } else {
            framesScore[frame - 1] += currentPinsDown;
        }
    }
}

class PlayGame {
    public static void main(String[] args) {
        BowlingGame game1 = new BowlingGame();
        int totalScore = game1.score();

        System.out.println("#####################################");
        System.out.println("### Total Score of the Game : " + totalScore + "  ###");
        System.out.println("#####################################");

        if (totalScore == 300) System.out.println("!!! It is a PERFECT GAME !!!");
    }

}
