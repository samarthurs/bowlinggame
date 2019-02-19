package com.genios.bowling;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class BowlingGameTest {

    private static BowlingGame game;
    @BeforeAll
    public static void setUp(){
        game = new BowlingGame();
    }

    @Test
    public void gutterGame(){
        String inputPins = "0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(0, game.score());
    }

    @Test
    public void scoreAllOnes(){
        String inputPins = "1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1 1";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(20, game.score());
    }

    @Test
    public void perfectGameTest(){
        String inputPins = "10 10 10 10 10 10 10 10 10 10 10 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(300, game.score());
    }

    @Test
    public void randomGameTest1(){
        String inputPins = "10 7 3 9 0 10 0 8 8 2 0 6 10 10 10 8 1";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(167, game.score());
    }

    @Test
    public void randomGameTest2(){
        String inputPins = "8 2 7 3 3 4 10 2 8 10 10 8 0 10 8 2 9";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(170, game.score());
    }

    @Test
    public void scoreAllSpares(){
        String inputPins = "6 4 8 2 4 6 6 4 5 5 2 8 4 6 5 5 6 4 8 2 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(158, game.score());
    }

    @Test
    public void alternateStrikeAndSpare(){
        String inputPins = "10 6 4 10 6 4 10 6 4 10 6 4 10 6 4 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(200, game.score());
    }

    @Test
    public void alternateSpareAndStrike(){
        String inputPins = "6 4 10 5 5 10 8 2 10 7 3 10 8 2 10 10 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(210, game.score());
    }

    @Test
    public void fiveSparesAndFiveStrikes(){
        String inputPins = "6 4 8 2 5 5 7 3 8 2 10 10 10 10 10 10 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(238, game.score());
    }

    @Test
    public void fiveStrikesAndFiveSpare(){
        String inputPins = "10 10 10 10 10 6 4 5 5 8 2 7 3 8 2 10";
        InputStream is = new ByteArrayInputStream(inputPins.getBytes());
        System.setIn(is);
        Assertions.assertEquals(224, game.score());
    }

}
