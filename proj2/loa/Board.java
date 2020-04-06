/* Skeleton Copyright (C) 2015, 2020 Paul N. Hilfinger and the Regents of the
 * University of California.  All rights reserved. */
package loa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;
import java.util.Stack;
import java.util.regex.Pattern;
import static loa.Piece.*;
import static loa.Square.*;

/**
 * Represents the state of a game of Lines of Action.
 *
 * @author Noe Trejo :)
 */
class Board {

    /**
     * Default number of moves for each side that results in a draw.
     */
    static final int DEFAULT_MOVE_LIMIT = 60;

    /**
     * Pattern describing a valid square designator (cr).
     */
    static final Pattern ROW_COL = Pattern.compile("^[a-h][1-8]$");

    /**
     * A Board whose initial contents are taken from INITIALCONTENTS
     * and in which the player playing TURN is to move. The resulting
     * Board has
     * get(col, row) == INITIALCONTENTS[row][col]
     * Assumes that PLAYER is not null and INITIALCONTENTS is 8x8.
     * <p>
     * CAUTION: The natural written notation for arrays initializers puts
     * the BOTTOM row of INITIALCONTENTS at the top.
     */
    Board(Piece[][] initialContents, Piece turn) {
        initialize(initialContents, turn);
    }

    /**
     * A new board in the standard initial position.
     */
    Board() {
        this(INITIAL_PIECES, BP);
    }

    /**
     * A Board whose initial contents and state are copied from
     * BOARD.
     */
    Board(Board board) {
        this();
        copyFrom(board);
    }

    /**
     * Set my state to CONTENTS with SIDE to move.
     */
    void initialize(Piece[][] contents, Piece side) {
        assert side != null && contents.length == BOARD_SIZE;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                _board[(i << 3) + j] = contents[i][j];
            }
        }
        _turn = side;
        _moveLimit = DEFAULT_MOVE_LIMIT;
        _subsetsInitialized = false;

    }

    /**
     * Set me to the initial configuration.
     */
    void clear() {
        initialize(INITIAL_PIECES, BP);
    }

    /**
     * Set my state to a copy of BOARD.
     */
    void copyFrom(Board board) {
        if (board == this) {
            return;
        }
        for (int i = 0; i < _board.length; i++) {
            _board[i] = board._board[i];
        }
        _moves.clear();
        _moves.addAll(board._moves);
        _winnerKnown = board._winnerKnown;
        _winner = board.winner();
        _subsetsInitialized = false;
        _captures = board._captures;
        _turn = board._turn;
    }

    /**
     * Return the contents of the square at SQ.
     */
    Piece get(Square sq) {
        return _board[sq.index()];
    }

    /**
     * Set the square at SQ to V and set the side that is to move next
     * to NEXT, if NEXT is not null.
     */
    void set(Square sq, Piece v, Piece next) {
        if (next != null) {
            _turn = next;
        }
        _board[sq.index()] = v;
    }

    /**
     * Set the square at SQ to V, without modifying the side that
     * moves next.
     */
    void set(Square sq, Piece v) {
        set(sq, v, null);
    }

    /**
     * Set limit on number of moves by each side that results in a tie to
     * LIMIT, where 2 * LIMIT > movesMade().
     */
    void setMoveLimit(int limit) {
        if (2 * limit <= movesMade()) {
            throw new IllegalArgumentException("move limit too small");
        }
        _moveLimit = 2 * limit;
    }

    /**
     * Assuming isLegal(MOVE), make MOVE. Assumes MOVE.isCapture()
     * is false.
     */
    void makeMove(Move move) {
        assert isLegal(move);
        _moves.add(move);
        Square from = move.getFrom();
        Square to = move.getTo();
        Piece replaced = get(from);
        Piece moveTo = get(to);
        if (move.isCapture() || moveTo == replaced.opposite()) {
            _captures.push(true);
        } else {
            _captures.push(false);
        }
        if (replaced != EMP) {
            set(to, EMP);
        }
        set(to, _turn);
        set(from, EMP);
        swapTurn();
        setWKandSIFalse();

    }

    /**
     * A stack that tells me whether a move was a capture or not.
     */
    private Stack<Boolean> _captures = new Stack<>();

    /** A simple function that sets the
     * _winner known to false and
     * _subsetsInitialized to false.*/
    private void setWKandSIFalse() {
        _winnerKnown = false;
        _subsetsInitialized = false;
    }

    /**
     * Retract (unmake) one move, returning to the state immediately before
     * that move.  Requires that movesMade () > 0.
     */
    void retract() {
        assert movesMade() > 0;
        Move move = _moves.remove(_moves.size() - 1);
        Square from = move.getFrom();
        Square to = move.getTo();
        Piece replaced = get(from);
        Piece moved = get(to);
        Boolean isCaptureLast = _captures.pop();
        if (isCaptureLast) {
            set(to, _turn);
            set(from, _turn.opposite());
        } else {
            set(to, EMP);
            set(from, moved);
        }
        swapTurn();
        setWKandSIFalse();
    }

    /**
     * Return the Piece representing who is next to move.
     */
    Piece turn() {
        return _turn;
    }

    /**
     * Swaps the turns.
     */
    void swapTurn() {
        _turn = _turn.opposite();
    }

    /**
     * Helper function for organization:
     * just returns an array list of a squares
     * directions from and to one another.
     *
     * @param from from square.
     * @param to   to square
     * @return an ArrayList with the directions
     * form and to a square
     */
    private ArrayList<Integer> directions(Square from, Square to) {
        ArrayList<Integer> directions = new ArrayList<>();
        if (from.isValidMove(to)) {
            directions.add(from.direction(to));
        }
        if (to.isValidMove(from)) {
            directions.add(to.direction(from));
        }
        return directions;
    }

    /**
     * A function that returns the number of pieces
     * of a side.
     *
     * @param side BP or WP.
     * @return number of that side of piece.
     */
    int numOfMyPC(Piece side) {
        int numofPieces = 0;
        for (Square sq : ALL_SQUARES) {
            Piece sqP = get(sq);
            if (sqP == side) {
                numofPieces++;
            }
        }
        return numofPieces;
    }

    /**
     * Getting the number of pieces on a line of action
     * behind and infront of a square using the direction.
     *
     * @param from the from square
     * @param to   the to square
     * @return the amount of pieces in the Line of action
     */
    private int piecesOnLOA(Square from, Square to) {
        int numOfPieces = 1;
        ArrayList<Integer> directions = directions(from, to);
        for (int dir : directions) {
            Square pointer = from.moveDest(dir, 1);
            while (pointer != null) {
                Piece pointerP = get(pointer);
                if (pointerP != EMP) {
                    numOfPieces++;
                }
                pointer = pointer.moveDest(dir, 1);
            }
        }
        return numOfPieces;
    }

    /**
     * Getting the number of pieces on a line of action
     * behind and infront of a square using the direction.
     *
     * @param move just in case I get lazy.
     * @return the amount of pieces in the Line of action.
     */
    private int piecesOnLOA(Move move) {
        return piecesOnLOA(move.getFrom(), move.getTo());
    }

    /** A function that does the asserts for
     * a function.
     * @param from the from square.
     * @param to the to square.
     */
    void assertCheck(Square from, Square to) {
        assert from != null && to != null
                && exists(from.row(), from.col())
                && exists(to.row(), to.col());
    }

    /**
     * Return true iff FROM - TO is a legal move for the player currently on
     * move.
     */
    boolean isLegal(Square from, Square to) {
        assertCheck(from, to);
        Piece moveTo = get(to);
        Piece fromP = get(from);
        int numOfPieces = piecesOnLOA(from, to);
        return fromP != EMP && moveTo != fromP
                && from.isValidMove(to)
                && numOfPieces == from.distance(to)
                && (moveTo == EMP || moveTo == _turn.opposite())
                && !blocked(from, to);
    }

    /**
     * Return true iff MOVE is legal for the player currently on move.
     * The isCapture() property is ignored.
     */
    boolean isLegal(Move move) {
        return isLegal(move.getFrom(), move.getTo());
    }

    /**
     * Return a sequence of all legal moves from this position.
     */
    List<Move> legalMoves() {
        ArrayList<Move> m = new ArrayList<>();
        ArrayList<Square> mySqs = new ArrayList<>();
        for (Square mySq : ALL_SQUARES) {
            for (Square moveableTo : ALL_SQUARES) {
                Piece myP = get(mySq);
                if (myP == _turn && isLegal(mySq, moveableTo)) {
                    m.add(Move.mv(mySq, moveableTo));
                }
            }
        }
        return m;
    }

    /**
     * Return true iff the game is over (either player has all his
     * pieces continguous or there is a tie).
     */
    boolean gameOver() {
        return winner() != null;
    }

    /**
     * Return true iff SIDE's pieces are continguous.
     */
    boolean piecesContiguous(Piece side) {
        return getRegionSizes(side).size() == 1;
    }

    /**
     * Return the winning side, if any.  If the game is not over, result is
     * null.  If the game has ended in a tie, returns EMP.
     */
    Piece winner() {
        if (!_winnerKnown) {
            boolean pcWP = piecesContiguous(WP);
            boolean pcBP = piecesContiguous(BP);
            if (pcWP && pcBP && movesMade() >= _moveLimit) {
                _winner = _turn.opposite();
            }
            if (pcWP) {
                _winner = WP;
            } else if (pcBP) {
                _winner = BP;
            } else if (movesMade() >= _moveLimit) {
                _winner = EMP;
            } else {
                _winner = null;
            }
            _winnerKnown = true;
        }
        return _winner;
    }

    /**
     * Return the total number of moves that have been made (and not
     * retracted).  Each valid call to makeMove with a normal move increases
     * this number by 1.
     */
    int movesMade() {
        return _moves.size();
    }

    @Override
    public boolean equals(Object obj) {
        Board b = (Board) obj;
        return Arrays.deepEquals(_board, b._board) && _turn == b._turn;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(_board) * 2 + _turn.hashCode();
    }

    @Override
    public String toString() {
        Formatter out = new Formatter();
        out.format("===%n");
        for (int r = BOARD_SIZE - 1; r >= 0; r -= 1) {
            out.format("    ");
            for (int c = 0; c < BOARD_SIZE; c += 1) {
                out.format("%s ", get(sq(c, r)).abbrev());
            }
            out.format("%n");
        }
        out.format("Next move: %s%n===", turn().fullName());
        return out.toString();
    }

    /**
     * Return true if a move from FROM to TO is blocked by an opposing
     * piece or by a friendly piece on the target square.
     */
    private boolean blocked(Square from, Square to) {
        assertCheck(from, to);
        Piece toP = get(to);
        Piece fromP = get(from);
        if (fromP != EMP && toP == fromP) {
            return true;
        }
        int dir = from.direction(to);
        int dist = from.distance(to);
        Square pointer = from.moveDest(dir, 1);
        while (pointer != null
                && pointer != to
                && from.distance(pointer) < dist) {
            Piece pointerP = get(pointer);
            if (pointerP.equals(fromP.opposite())) {
                return true;
            }
            pointer = pointer.moveDest(dir, 1);
        }
        return false;
    }
    /** A helper function for finding the
     * region sizes of a region.
     * @param  p a piece.
     * @param  region a side
     * */
    private void findSqrConfig(Piece p, ArrayList<Integer> region) {
        boolean[][] bool = new boolean[8][8];
        for (Square sq : ALL_SQUARES) {
            int numOfContig = numContig(sq, bool, p);
            if (numOfContig != 0) {
                region.add(numOfContig);
            }
        }
    }

    /**
     * Return the size of the as-yet unvisited cluster of squares
     * containing P at and adjacent to SQ.  VISITED indicates squares that
     * have already been processed or are in different clusters.  Update
     * VISITED to reflect squares counted.
     */
    private int numContig(Square sq, boolean[][] visited, Piece p) {
        int count = 1;
        if (p == EMP) {
            return 0;
        }
        boolean visisted = visited[sq.col()][sq.row()];
        if (visisted) {
            return 0;
        }
        if (get(sq) != p) {
            return 0;
        }
        visited[sq.col()][sq.row()] = true;
        for (Square adj : sq.adjacent()) {
            count += numContig(adj, visited, p);
        }

        return count;
    }

    /**
     * Set the values of _whiteRegionSizes and _blackRegionSizes.
     */
    private void computeRegions() {
        if (_subsetsInitialized) {
            return;
        }
        _whiteRegionSizes.clear();
        _blackRegionSizes.clear();
        findSqrConfig(WP, _whiteRegionSizes);
        findSqrConfig(BP, _blackRegionSizes);
        Collections.sort(_whiteRegionSizes, Collections.reverseOrder());
        Collections.sort(_blackRegionSizes, Collections.reverseOrder());
        _subsetsInitialized = true;
    }


    /**
     * Return the sizes of all the regions in the current union-find
     * structure for side S.
     */
    List<Integer> getRegionSizes(Piece s) {
        computeRegions();
        if (s == WP) {
            return _whiteRegionSizes;
        } else {
            return _blackRegionSizes;
        }
    }

    /**
     * The standard initial configuration for Lines of Action (bottom row
     * first).
     */
    static final Piece[][] INITIAL_PIECES = {
            {EMP, BP, BP, BP, BP, BP, BP, EMP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {WP, EMP, EMP, EMP, EMP, EMP, EMP, WP},
            {EMP, BP, BP, BP, BP, BP, BP, EMP}
    };

    /**
     * Current contents of the board.  Square S is at _board[S.index()].
     */
    private final Piece[] _board = new Piece[BOARD_SIZE * BOARD_SIZE];

    /**
     * List of all unretracted moves on this board, in order.
     */
    private final ArrayList<Move> _moves = new ArrayList<>();
    /**
     * Current side on move.
     */
    private Piece _turn;
    /**
     * Limit on number of moves before tie is declared.
     */
    private int _moveLimit;
    /**
     * True iff the value of _winner is known to be valid.
     */
    private boolean _winnerKnown;
    /**
     * Cached value of the winner (BP, WP, EMP (for tie), or null (game still
     * in progress).  Use only if _winnerKnown.
     */
    private Piece _winner;

    /**
     * True iff subsets computation is up-to-date.
     */
    private boolean _subsetsInitialized;

    /**
     * List of the sizes of continguous clusters of pieces, by color.
     */
    private final ArrayList<Integer>
            _whiteRegionSizes = new ArrayList<>(),
            _blackRegionSizes = new ArrayList<>();
}
