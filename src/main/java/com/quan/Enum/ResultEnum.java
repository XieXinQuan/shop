package com.quan.Enum;
/**
 *
 * @author: xiexinquan520@163.com
 * User: XieXinQuan
 * DATE:2020/1/11
 */
public enum  ResultEnum implements BaseEnum{
    /**
     * 返回枚举
     */
    Success(1, "Success"),
    CustomException(2, "Custom Exception"),
    PromptInformation(3, "Prompt Information"),
    LoginOverDue(4, "Login Over Due"),
    GoToLogin(5, "Go To Login"),
    UserIsNotExists(6, "User Is Not Exists"),
    PasswordIsError(7, "Password Is Error"),
    ParameterError(7, "Parameter Error"),
    ApplicationMember(8, "Go To Member Application"),
    NoRequestMapper(404, "Go To Login"),
    SystemException(500, "System Exception");
    private Integer key;
    private String value;

    ResultEnum(Integer key, String value) {
        this.key = key;
        this.value = value;
    }

    /**
     * 获取枚举值
     * @return
     */
    @Override
    public Integer getKey() {
        return key;
    }

    /**
     * 获取枚举解释
     * @return
     */
    @Override
    public String getValue() {
        return value;
    }
}
