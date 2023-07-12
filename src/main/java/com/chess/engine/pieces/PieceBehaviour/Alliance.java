package com.chess.engine.pieces.PieceBehaviour;

import com.chess.engine.Player.Player;

public enum Alliance implements Behaviour{
    WHITE {
        @Override
        public int getDirection() {
            return -1;
        }

        @Override
        public boolean isBlack() {
            return false;
        }

        @Override
        public boolean isWhite() {
            return true;
        }

        @Override
        public Player choosePlayer(final Player whitePlayer,
                                   final Player blackPlayer) {
            return whitePlayer;
        }
    },
    BLACK {
        @Override
       public int getDirection() {
            return 1;
        }

        @Override
        public boolean isBlack() {
            return true;
        }

        @Override
        public boolean isWhite() {
            return false;
        }

        @Override
        public Player choosePlayer(final Player whitePlayer,
                                   final Player blackPlayer) {
            return blackPlayer;
        }
    };

    public abstract int getDirection();
    public abstract boolean isBlack();
    public abstract boolean isWhite();

    public abstract Player choosePlayer(Player whitePlayer, Player blackPlayer);



}
