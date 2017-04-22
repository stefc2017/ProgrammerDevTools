package com.stefancouture.programmerdevtools;

public class TableInformation {
    private static int start; //index to start displaying from
    private static int lengthOfData; //if true = next, false = previous
    private static int displayNumber; //increment/decrement by how much
    private static int indexOfLastData; //index of last data shown in table on the page
    private static String columnSortBy;
    private static String order;

    public TableInformation(){}

    public int getIndexOfLastData(){
        return indexOfLastData;
    }

    public void setIndexOfLastData(int index){
        indexOfLastData = index;
    }
    public String getcolumnSortBy(){
        return columnSortBy;
    }

    public void setcolumnSortBy(String col){
        columnSortBy = col;
    }

    public String getOrder(){
        return order;
    }

    public void setOrder(String order){
        this.order = order;
    }

    public int getStart(){
        return start;
    }

    public int getLengthOfData(){
        return lengthOfData;
    }

    public int getDisplayNumber(){
        return displayNumber;
    }

    public void setStart(int start){
        this.start = start;
    }

    public void setLengthOfData(int length){
        lengthOfData = length;
    }

    public void setDisplayNumber(int displayNumber){
        this.displayNumber = displayNumber;
    }

    public void incrementStart(int incrementBy){
        start += incrementBy;
    }

    public void decrementStart(int decrementBy){
        start -= decrementBy;
    }
}
