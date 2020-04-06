/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.Collections;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static loa.Piece.*;

/**
 * An automated Player.
 *
 * @author Noe Trejo :)
 */
class MachinePlayer extends Player {

    /**
     * A position-score magnitude indicating a win (for white if positive,
     * black if negative).
     */
    private static final int WINNING_VALUE = Integer.MAX_VALUE - 20;
    /**
     * A magnitude greater than a normal value.
     */
    private static final int INFTY = Integer.MAX_VALUE;

    /**
     * A new MachinePlayer with no piece or controller (intended to produce
     * a template).
     */
    MachinePlayer() {
        this(null, null);
    }

    /**
     * A MachinePlayer that plays the SIDE pieces in GAME.
     */
    MachinePlayer(Piece side, Game game) {
        super(side, game);
    }

    @Override
    String getMove() {
        Move choice;
        assert side() == getGame().getBoard().turn();
        int depth;
        choice = searchForMove();
        getGame().reportMove(choice);
        return choice.toString();
    }

    @Override
    Player create(Piece piece, Game game) {
        return new MachinePlayer(piece, game);
    }

    @Override
    boolean isManual() {
        return false;
    }

    /**
     * Return a move after searching the game tree to DEPTH>0 moves
     * from the current position. Assumes the game is not over.
     */
    private Move searchForMove() {
        Board work = new Board(getBoard());
        int value;
        assert side() == work.turn();
        _foundMove = null;
        if (side() == WP) {
            value = findMove(work, chooseDepth(), true, 1, -INFTY, INFTY);
        } else {
            value = findMove(work, chooseDepth(), true, -1, -INFTY, INFTY);
        }
        return _foundMove;
    }

    /**
     * Helper function that just makes a copy of
     * a board and does a move.
     *
     * @param b a board
     * @param m a move that gets done to the new board
     * @return the board that's a copy
     * of the board passed in with one "m" move
     * done to it.
     */
    private Board copyBMakeMove(Board b, Move m) {
        Board board = new Board();
        board.copyFrom(b);
        board.makeMove(m);
        return board;
    }

    /**
     * Find a move from position BOARD and return its value, recording
     * the move found in _foundMove iff SAVEMOVE. The move
     * should have maximal value or have value > BETA if SENSE==1,
     * and minimal value or value < ALPHA if SENSE==-1. Searches up to
     * DEPTH levels.  Searching at level 0 simply returns a static estimate
     * of the board value and does not set _foundMove. If the game is over
     * on BOARD, does not set _foundMove.
     */
    private int findMove(Board board, int depth, boolean saveMove,
                         int sense, int alpha, int beta) {
        if (depth == 0) {
            return heuristic(board);
        }
        int bestScore;
        if (sense == 1) {
            bestScore = -INFTY;
        } else {
            bestScore = INFTY;
        }
        List<Move> moves = board.legalMoves();
        for (Move m : moves) {
            Board updatedB = copyBMakeMove(board, m);
            int score = findMove(updatedB, depth - 1,
                    false, sense * -1, alpha, beta);
            if (sense == 1) {
                alpha = max(score, alpha);
                if (score > bestScore) {
                    bestScore = score;
                    if (saveMove) {
                        _foundMove = m;
                    }
                }
            } else {
                beta = min(score, beta);
                if (score < bestScore) {
                    bestScore = score;
                    if (saveMove) {
                        _foundMove = m;
                    }
                }
            }
            if (alpha >= beta) {
                break;
            }
        }
        return bestScore;
    }

    /**
     * Return a search depth for the current position.
     */
    private int chooseDepth() {
        return 4;
    }
    /** A heuristic function that
     * basically tells my AI if it's doing
     * a good job or bad.
     * @param b a board
     * @return a heuritistic value*/
    private int heuristic(Board b) {
        Piece mySide = side();
        int heuristic = 0;
        Piece opponentSide = side().opposite();
        if (b.gameOver()) {
            if (b.winner() == mySide) {
                return WINNING_VALUE;
            }
            if (b.winner() == opponentSide) {
                return -WINNING_VALUE;
            }
        }

        int numOfMyP = b.numOfMyPC(side());
        int numofOppP = b.numOfMyPC(side().opposite());
        List<Integer> myContigRegs = b.getRegionSizes(mySide);
        List<Integer> oppContigRegs = b.getRegionSizes(mySide.opposite());
        int myBContigReg = Collections.max(myContigRegs);
        int opponentsBContigReg = Collections.max(oppContigRegs);
        if (myContigRegs.size() > oppContigRegs.size()) {
            heuristic++;
        }
        if (myBContigReg > opponentsBContigReg) {
            heuristic++;
        }
        if (numOfMyP > numofOppP) {
            heuristic++;
        }
        return heuristic;
    }
    /**
     * Used to convey moves discovered by findMove.
     */
    private Move _foundMove;

}
