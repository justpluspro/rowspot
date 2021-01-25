package org.qwli.rowspot;

import java.io.Serializable;

/**
 * restapi 返回结果处理
 * @author liqiwen
 */
public class ResultDto implements Serializable {

    /**
     * 返回 msg
     */
    private String msg;

    /**
     * 返回 code
     */
    private Integer code;

    /**
     * 返回业务数据
     */
    private Object data;


    public static ResultDto success(String ... msg) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ResultEnum.SUCCESS.getCode());
        resultDto.setMsg(msg == null || msg.length == 0 ? ResultEnum.SUCCESS.getMsg() : msg[0]);
        return resultDto;
    }

    public static ResultDto success(Object data) {
        ResultDto resultDto = new ResultDto();
        resultDto.setCode(ResultEnum.SUCCESS.getCode());
        resultDto.setMsg(ResultEnum.SUCCESS.getMsg());
        resultDto.setData(data);
        return resultDto;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
