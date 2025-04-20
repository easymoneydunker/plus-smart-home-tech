package ru.yandex.practicum.dimension.model;

import jakarta.persistence.Embeddable;

@Embeddable
public class Dimension {
    private double width;
    private double height;
    private double depth;

    public Dimension(double width, double height, double depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public Dimension() {
    }

    public static DimensionBuilder builder() {
        return new DimensionBuilder();
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

    public double getDepth() {
        return this.depth;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setDepth(double depth) {
        this.depth = depth;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Dimension)) return false;
        final Dimension other = (Dimension) o;
        if (!other.canEqual((Object) this)) return false;
        if (Double.compare(this.getWidth(), other.getWidth()) != 0) return false;
        if (Double.compare(this.getHeight(), other.getHeight()) != 0) return false;
        if (Double.compare(this.getDepth(), other.getDepth()) != 0) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Dimension;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final long $width = Double.doubleToLongBits(this.getWidth());
        result = result * PRIME + (int) ($width >>> 32 ^ $width);
        final long $height = Double.doubleToLongBits(this.getHeight());
        result = result * PRIME + (int) ($height >>> 32 ^ $height);
        final long $depth = Double.doubleToLongBits(this.getDepth());
        result = result * PRIME + (int) ($depth >>> 32 ^ $depth);
        return result;
    }

    public String toString() {
        return "Dimension(width=" + this.getWidth() + ", height=" + this.getHeight() + ", depth=" + this.getDepth() + ")";
    }

    public static class DimensionBuilder {
        private double width;
        private double height;
        private double depth;

        DimensionBuilder() {
        }

        public DimensionBuilder width(double width) {
            this.width = width;
            return this;
        }

        public DimensionBuilder height(double height) {
            this.height = height;
            return this;
        }

        public DimensionBuilder depth(double depth) {
            this.depth = depth;
            return this;
        }

        public Dimension build() {
            return new Dimension(this.width, this.height, this.depth);
        }

        public String toString() {
            return "Dimension.DimensionBuilder(width=" + this.width + ", height=" + this.height + ", depth=" + this.depth + ")";
        }
    }
}