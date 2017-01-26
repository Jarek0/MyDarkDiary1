package myDarkDiary.service.notUsed;

public interface SecurityService {
    String findLoggedInUsername();

    void autologin(String username, String password);
}
