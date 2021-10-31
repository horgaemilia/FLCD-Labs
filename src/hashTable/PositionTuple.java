package hashTable;

public class PositionTuple {
    private int bucketPosition;
    private int inBucketPosition;
    public PositionTuple(int bucketPosition,int inBucketPosition)
    {
        this.bucketPosition = bucketPosition;
        this.inBucketPosition = inBucketPosition;
    }

    public void setBucketPosition(int bucketPosition)
    {
        this.bucketPosition = bucketPosition;
    }

    public void setInBucketPosition(int inBucketPosition)
    {
        this.inBucketPosition = inBucketPosition;
    }

    public int getInBucketPosition() {
        return inBucketPosition;
    }

    public int getBucketPosition() {
        return bucketPosition;
    }

    @Override
    public String toString() {
        return "(" + bucketPosition + "," + inBucketPosition + ")";
    }
}
