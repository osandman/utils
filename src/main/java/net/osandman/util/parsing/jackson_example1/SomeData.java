package net.osandman.util.parsing.jackson_example1;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

@JsonAutoDetect
public class SomeData {
//    @JsonProperty("new name")
    private InnerData innerData;
    private String param1;
    private int param2;
    @JsonIgnore
    private boolean param3;
    private float param4;

    public SomeData() {
    }

    public SomeData(String param1, int param2, boolean param3, float param4, InnerData innerData) {
        this.innerData = innerData;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
        this.param4 = param4;
    }

    public String getParam1() {
        return param1;
    }

    public int getParam2() {
        return param2;
    }

    public boolean isParam3() {
        return param3;
    }

    public float getParam4() {
        return param4;
    }

    public InnerData getInnerData() {
        return innerData;
    }


    @Override
    public String toString() {
        return "SomeData{" +
               "param1='" + param1 + '\'' +
               ", param2=" + param2 +
               ", param3=" + param3 +
               ", param4=" + param4 +
               ", innerData=" + innerData.toString() +
               '}';
    }

    static class InnerData {
        private String innerParam1;
        private List innerParam2;

        public InnerData() {
        }

        public InnerData(String innerParam1, List innerParam2) {
            this.innerParam1 = innerParam1;
            this.innerParam2 = innerParam2;
        }

        public String getInnerParam1() {
            return innerParam1;
        }

        @Override
        public String toString() {
            return "InnerData{" +
                   "innerParam1='" + innerParam1 + '\'' +
                   ", innerParam2=" + innerParam2 +
                   '}';
        }

        public List getInnerParam2() {
            return innerParam2;
        }

    }

}
