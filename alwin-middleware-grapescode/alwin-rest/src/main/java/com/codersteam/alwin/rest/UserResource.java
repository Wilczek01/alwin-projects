package com.codersteam.alwin.rest;

import com.codersteam.alwin.auth.annotation.Secured;
import com.codersteam.alwin.auth.model.LoginRequest;
import com.codersteam.alwin.auth.model.LoginResponse;
import com.codersteam.alwin.core.api.model.common.Page;
import com.codersteam.alwin.core.api.model.operator.EditableOperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorDto;
import com.codersteam.alwin.core.api.model.operator.OperatorTypeDto;
import com.codersteam.alwin.core.api.model.user.EditableUserDto;
import com.codersteam.alwin.core.api.model.user.FullUserDto;
import com.codersteam.alwin.core.api.model.user.UserDto;
import com.codersteam.alwin.core.api.service.auth.AuthenticationService;
import com.codersteam.alwin.core.api.service.auth.JwtService;
import com.codersteam.alwin.core.api.service.user.UserService;
import com.codersteam.alwin.validator.UserValidator;
import io.swagger.annotations.*;
import io.swagger.jaxrs.PATCH;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.Optional;

import static com.codersteam.alwin.core.api.model.user.OperatorType.ADMIN;
import static com.codersteam.alwin.core.api.service.auth.JwtService.BEARER_HEADER_PREFIX;
import static java.lang.String.format;
import static javax.ws.rs.core.HttpHeaders.AUTHORIZATION;
import static javax.ws.rs.core.MediaType.APPLICATION_JSON;
import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;
import static org.apache.commons.collections.CollectionUtils.isEmpty;

@Path("/users")
@Consumes(APPLICATION_JSON)
@Produces(APPLICATION_JSON)
@Api("/users")
public class UserResource {

    public static final String BEARER_PREFIX = BEARER_HEADER_PREFIX + " ";
    public static final String ACCESS_CONTROL_EXPOSE_HEADERS = "Access-Control-Expose-Headers";
    public static final String USER_NAME_FORMAT = "%s %s";

    private JwtService jwtService;
    private AuthenticationService authenticationService;
    private UserService userService;
    private UserValidator userValidator;

    public UserResource() {
    }

    @Inject
    public UserResource(final JwtService jwtService, final AuthenticationService authenticationService, final UserService userService, final UserValidator userValidator) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @POST
    @Path("login")
    @PermitAll
    @ApiOperation("Logowanie")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Sukces", responseHeaders = @ResponseHeader(name = AUTHORIZATION, description = "Token", response = String.class), response = LoginResponse.class),
            @ApiResponse(code = 401, message = "Błąd logowania")})
    public Response login(@ApiParam(required = true) final LoginRequest loginRequest) {

        final String userName = loginRequest.getUsername();
        final String password = loginRequest.getPassword();
        final Optional<OperatorDto> operatorDto = authenticationService.authenticateUser(userName, password);

        return operatorDto.map(operator -> {
            final OperatorTypeDto operatorType = operator.getType();
            final String token = jwtService.createToken(userName, operatorType.getTypeName(), operator.getId(), operator.getUser().getFirstName(),
                    operator.getUser().getLastName());
            final String displayUserName = prepareDisplayUserName(operator);
            final LoginResponse response = new LoginResponse(displayUserName, operatorType, operator.isForceUpdatePassword(), operator.getPermission());
            return Response.ok().header(AUTHORIZATION, BEARER_PREFIX + token).header(ACCESS_CONTROL_EXPOSE_HEADERS, AUTHORIZATION).entity(response).build();
        }).orElseGet(() -> Response.status(UNAUTHORIZED).build());
    }

    @GET
    @Secured({ADMIN})
    @ApiOperation("Pobranie stronicowanej listy użytkowników wraz z ich operatorami. Użytkowników można filtrować po loginie, imieniu, nazwisku lub imieniu i nazwisku razem oddzielonymi spacją.")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Page<FullUserDto> findAllUsers(@ApiParam(name = "firstResult", value = "Pierwszy element", defaultValue = "0")
                                          @QueryParam("firstResult") int firstResult,
                                          @ApiParam(name = "maxResults", value = "Maksymalna ilość per strona", defaultValue = "5")
                                          @QueryParam("maxResults") int maxResults,
                                          @ApiParam(name = "searchText", value = "Wyszukiwany ciag znaków")
                                          @QueryParam("searchText") String searchText) {
        final Page<FullUserDto> users;
        if (StringUtils.isEmpty(searchText)) {
            users = userService.findAllUsers(firstResult, maxResults);
        } else {
            users = userService.findAllUsersFilterByNameAndLogin(firstResult, maxResults, searchText);
        }
        return users;
    }

    @POST
    @Secured({ADMIN})
    @ApiOperation("Dodanie nowego użytkownika")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response createUser(@ApiParam(required = true, value = "Użytkownik do utworzenia") final UserDto user) {
        userService.createUser(user);
        return Response.created(null).build();
    }

    @PATCH
    @Path("/{userId}")
    @Secured({ADMIN})
    @ApiOperation("Edycja użytkownika wraz z operatorami")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public Response updateUser(@ApiParam(name = "userId", value = "Identyfikator użytkownika") @PathParam("userId") final long userId,
                               @ApiParam(required = true, value = "Użytkownik wraz z operatorami do edycji") final EditableUserDto user) {
        userValidator.validate(user, userId);
        userService.updateUser(user);
        return Response.accepted().build();
    }

    @GET
    @Path("/{userId}")
    @Secured({ADMIN})
    @ApiOperation("Pobranie użytkownika o podanym identyfikatorze wraz z jego operatorami")
    @ApiImplicitParams({@ApiImplicitParam(name = AUTHORIZATION, value = "Token", dataType = "string", paramType = "header")})
    public EditableUserDto findUser(@ApiParam(name = "userId", value = "Identyfikator użytkownika") @PathParam("userId") final Long userId) {
        userValidator.validateIfUserExists(userId);

        final EditableUserDto user = userService.findUser(userId).get();
        if (!isEmpty(user.getOperators())) {
            for (final EditableOperatorDto operator : user.getOperators()) {
                operator.setPassword(null);
                operator.setSalt(null);
            }
        }
        return user;
    }

    private String prepareDisplayUserName(final OperatorDto user) {
        return format(USER_NAME_FORMAT, user.getUser().getFirstName(), user.getUser().getLastName());
    }
}
