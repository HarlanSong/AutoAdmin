package cn.songhaiqing.tool.base;


/**
 * 通用的返回信息，
 * 调用HTTP接口等统一的响应（返回）信息。
 *
 */
public class BaseResponse {

	private int code = 0;
	private String msg = "";
    
	/**返回的数据*/
	private Object data;
	
	/**
	 * 默认构造函数。
	 */
	public BaseResponse() {
		
	}

	/**
	 * 指定返回数据的构造函数。
	 * @param msg
	 * @param data
	 */
	public BaseResponse(int code, String msg, Object data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
}
