package dto;

public class AuthDTO {

    public record LoginReq(String email, String password) {}

    public record LoginResp(String message, String token) {}
}
