package id.co.blogspot.fathan.controller.authentication;

import id.co.blogspot.fathan.dto.BaseResponse;
import id.co.blogspot.fathan.dto.SingleBaseResponse;
import id.co.blogspot.fathan.service.user.UserService;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fathan.mustaqiim on 10/24/2016.
 */
@RestController
@RequestMapping(value = AuthenticationControllerPath.BASE_PATH)
public class AuthenticationController {

  @Autowired
  private UserService userService;

  @RequestMapping(value = AuthenticationControllerPath.LOGIN, method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public SingleBaseResponse<String> authenticate(
      @RequestParam(required = false) String requestId, @RequestParam(required = false) String ticket,
      HttpServletResponse httpServletResponse)
      throws Exception {
    String jwtToken = this.userService.authenticate(ticket, httpServletResponse);
    return new SingleBaseResponse<String>(null, null, true, requestId, jwtToken);
  }

  @RequestMapping(value = AuthenticationControllerPath.LOGOUT, method = RequestMethod.GET, produces = {
      MediaType.APPLICATION_JSON_VALUE})
  public BaseResponse unauthenticate(@RequestParam String requestId) throws Exception {
    this.userService.unauthenticate();
    return new BaseResponse(null, null, true, requestId);
  }
}
