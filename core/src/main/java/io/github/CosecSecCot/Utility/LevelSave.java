package io.github.CosecSecCot.Utility;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LevelSave implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    public int score;

    // Stores bird data
    public static class BirdData implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public String type;
        public float x, y;

        public BirdData(String type, float x, float y) {
            this.type = type;
            this.x = x;
            this.y = y;
        }
    }

    public static class PigData implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public String type;
        public float x, y;
        public float angle;

        public PigData(String type, float x, float y, float angle) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.angle = angle;
        }
    }

    public static class BlockData implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        public String type;
        public float x, y;
        public float rotation;
        public float velocityX, velocityY;

        public BlockData(String type, float x, float y, float rotation, float velocityX, float velocityY) {
            this.type = type;
            this.x = x;
            this.y = y;
            this.rotation = rotation;
            this.velocityX = velocityX;
            this.velocityY = velocityY;
        }
    }

    public List<BirdData> birds = new ArrayList<>();
    public List<PigData> pigs = new ArrayList<>();
    public List<BlockData> blocks = new ArrayList<>();
}
