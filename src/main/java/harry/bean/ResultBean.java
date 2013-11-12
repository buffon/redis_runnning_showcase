package harry.bean;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: chenyehui
 * Date: 13-11-12
 * Time: 上午10:22
 * To change this template use File | Settings | File Templates.
 */
public class ResultBean implements Serializable{

    public ResultBean(String value){
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public ResultBean(String value,String pass) {
        this.value = value;
        this.pass = pass;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @JsonProperty
    private String value;

    @JsonProperty
    private String pass;

}
