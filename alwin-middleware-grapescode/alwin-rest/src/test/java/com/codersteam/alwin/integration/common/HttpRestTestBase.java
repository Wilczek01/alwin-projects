package com.codersteam.alwin.integration.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.codehaus.jackson.JsonEncoding;

import javax.ws.rs.core.HttpHeaders;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codersteam.alwin.integration.common.ITTestUtil.getFile;
import static com.codersteam.alwin.integration.common.ITTestUtil.getResponseFile;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Michal Horowic
 */
public abstract class HttpRestTestBase extends TestBase {

    private static final String REST_URL = "http://localhost:8380/alwin-rest/";
    private static final JsonParser PARSER = new JsonParser();
    /**
     * Flaga mówiąca czy mają być nadpisane pliki json ze spodziewanymi odpowiedziami z serwer dla testów integracyjnych, które nie przechodzą.
     * Po ustawieniu flagi na true, należy uruchomić testy żeby zapisały się odpowiedzi serwera do plików.
     * Następnie ustawiamy flagę na false i sprawdzamy czy zmienione pliki są tak jak się tego spodziewaliśmy.
     * Przydatne po zmianie dto, który wpływa na wiele plików json.
     */
    private static final boolean WRITE_JSON_TO_FILE_WHEN_ASSERT_ERROR_OCCURRED = false;
    private final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new ValidatorAdapterFactory())
            .registerTypeAdapter(Date.class, new GsonDateTypeAdapter()).create();

    protected String loggedInAdmin() throws IOException {
        return loggedInUser("user/validLogin.json");
    }

    protected String loggedInPhoneDebtCollectorManager() throws IOException {
        return loggedInUser("user/validPhoneDebtCollectorManagerLogin.json");
    }

    protected String loggedInFieldDebtCollector() throws IOException {
        return loggedInUser("user/validFieldDebtCollectorLogin.json");
    }

    protected String loggedInFieldDebtCollectorManager() throws IOException {
        return loggedInUser("user/validFieldDebtCollectorManagerLogin.json");
    }

    protected String loggedInUserWithoutIssues() throws IOException {
        return loggedInUser("user/validLoginWithoutIssues.json");
    }

    protected String loggedInWithIssues() throws IOException {
        return loggedInUser("user/validLoginWithIssues.json");
    }

    private String loggedInUser(final String requestName) throws IOException {
        final JsonElement request = request(requestName);
        final Response response = getHttpPostResponse("users/login", request);
        final HttpResponse httpResponse = response.returnResponse();
        final Header[] authorizationHeaders = httpResponse.getHeaders(HttpHeaders.AUTHORIZATION);
        assertNotNull("Unable to log in default user, no authorization header", authorizationHeaders);
        assertEquals("Unable to log in default user, no authorization header", 1, authorizationHeaders.length);
        return authorizationHeaders[0].getValue();
    }

    protected JsonElement sendHttpGet(final String path) throws IOException {
        final String response = Request.Get(REST_URL + path).execute().returnContent().asString(UTF_8);
        return PARSER.parse(response);
    }

    protected <T> T sendHttpGet(final String path, final Class<T> responseClass) throws IOException {
        final JsonElement response = sendHttpGet(path);
        return gson.fromJson(response, responseClass);
    }

    protected <T> T sendHttpGet(final String path, final String loginToken, final Class<T> responseClass) throws IOException {
        final JsonElement response = sendHttpGet(path, loginToken);
        return gson.fromJson(response, responseClass);
    }

    protected JsonElement sendHttpGet(final String path, final String loginToken) throws IOException {
        final Request request = Request.Get(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        final String response = request.execute().returnContent().asString(UTF_8);
        return PARSER.parse(response);
    }

    protected String sendHttpGetForTextResponse(final String path, final String loginToken) throws IOException {
        final Request request = Request.Get(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute().returnContent().asString(UTF_8);
    }

    protected int checkHttpGetCode(final String path) throws IOException {
        final Response response = Request.Get(REST_URL + path).execute();
        return response.returnResponse().getStatusLine().getStatusCode();
    }

    protected int checkHttpGetCode(final String path, final String loginToken) throws IOException {
        final Request request = Request.Get(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute().returnResponse().getStatusLine().getStatusCode();
    }

    private Response getHttpPostResponse(final String path, final JsonElement body) throws IOException {
        return Request.Post(REST_URL + path).bodyString(body.toString(), ContentType.APPLICATION_JSON).execute();
    }

    protected JsonElement sendHttpPost(final String path, final JsonElement body) throws IOException {
        final String responseContent = getHttpPostResponse(path, body).returnContent().asString(UTF_8);
        return PARSER.parse(responseContent);
    }

    protected JsonElement sendHttpPatch(final String path, final String loginToken) throws IOException {
        final Request request = Request.Patch(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        final String response = request.execute().returnContent().asString(UTF_8);
        return PARSER.parse(response);
    }

    private Response getHttpPostResponse(final String path, final JsonElement body, final String loginToken) throws IOException {
        final Request request = Request.Post(REST_URL + path).bodyString(body.toString(), ContentType.APPLICATION_JSON);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute();
    }

    private Response getHttpPutResponse(final String path, final JsonElement body, final String loginToken) throws IOException {
        final Request request = Request.Put(REST_URL + path).bodyString(body.toString(), ContentType.APPLICATION_JSON);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute();
    }

    private Response getHttpGetResponse(final String path, final String loginToken) throws IOException {
        final Request request = Request.Get(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute();
    }

    protected JsonElement sendHttpPost(final String path, final JsonElement body, final String loginToken) throws IOException {
        final String responseContent = getHttpPostResponse(path, body, loginToken).returnContent().asString(UTF_8);
        return PARSER.parse(responseContent);
    }

    protected <T> T sendHttpPost(final String path, final JsonElement body, final String loginToken, final Class<T> responseClass) throws IOException {
        final String responseContent = getHttpPostResponse(path, body, loginToken).returnContent().asString(UTF_8);
        final JsonElement jsonElement = PARSER.parse(responseContent);
        return gson.fromJson(jsonElement, responseClass);
    }

    protected int checkHttpPostCode(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPostResponse(path, body, loginToken).returnResponse().getStatusLine().getStatusCode();
    }

    protected int checkHttpPutCode(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPutResponse(path, body, loginToken).returnResponse().getStatusLine().getStatusCode();
    }

    protected HttpResponse checkHttpPost(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPostResponse(path, body, loginToken).returnResponse();
    }

    protected <T> T checkHttpPost(final String path, final JsonElement body, final String loginToken, final Class<T> responseClass) throws IOException {
        final HttpResponse httpResponse = getHttpPostResponse(path, body, loginToken).returnResponse();
        final JsonElement jsonElement = bodyOf(httpResponse);
        return gson.fromJson(jsonElement, responseClass);
    }

    protected <T> T checkHttpGet(final String path, final String loginToken, final Class<T> responseClass) throws IOException {
        final HttpResponse httpResponse = getHttpGetResponse(path, loginToken).returnResponse();
        final JsonElement jsonElement = bodyOf(httpResponse);
        return gson.fromJson(jsonElement, responseClass);
    }

    protected HttpResponse checkHttpGet(final String path, final String loginToken) throws IOException {
        return getHttpGetResponse(path, loginToken).returnResponse();
    }

    protected HttpResponse checkHttpPatch(final String path, final String loginToken) throws IOException {
        return getHttpPatchResponse(path, null, loginToken).returnResponse();
    }

    protected HttpResponse checkHttpPatch(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPatchResponse(path, body, loginToken).returnResponse();
    }

    protected HttpResponse checkHttpDelete(final String path, final String loginToken) throws IOException {
        return getHttpDeleteResponse(path, loginToken).returnResponse();
    }

    protected int checkHttpPostCode(final String path, final JsonElement body) throws IOException {
        return getHttpPostResponse(path, body).returnResponse().getStatusLine().getStatusCode();
    }

    protected int checkHttpPatchCode(final String path, final String loginToken) throws IOException {
        return getHttpPatchResponse(path, null, loginToken).returnResponse().getStatusLine().getStatusCode();
    }

    protected int checkHttpPatchCode(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPatchResponse(path, body, loginToken).returnResponse().getStatusLine().getStatusCode();
    }

    protected int checkHttpDeleteCode(final String path, final String loginToken) throws IOException {
        return getHttpDeleteResponse(path, loginToken).returnResponse().getStatusLine().getStatusCode();
    }

    private Response getHttpPatchResponse(final String path, final JsonElement body, final String loginToken) throws IOException {
        final Request request = Request.Patch(REST_URL + path).bodyString(body != null ? body.toString() : StringUtils.EMPTY, ContentType.APPLICATION_JSON);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute();
    }

    private Response getHttpDeleteResponse(final String path, final String loginToken) throws IOException {
        final Request request = Request.Delete(REST_URL + path);
        request.addHeader(HttpHeaders.AUTHORIZATION, loginToken);
        return request.execute();
    }

    private JsonElement expectedResponse(final String name) throws IOException {
        return getParsedJsonFile("/messages/response/" + name);
    }

    protected JsonElement request(final String name) throws IOException {
        return getParsedJsonFile("/messages/request/" + name);
    }

    private JsonElement getParsedJsonFile(final String path) throws IOException {
        return PARSER.parse(getResponseFile(path));
    }

    protected HttpResponse sendHttpPostForResponse(final String path, final JsonElement body) throws IOException {
        return getHttpPostResponse(path, body).returnResponse();
    }

    protected HttpResponse sendHttpPostForResponse(final String path, final JsonElement body, final String loginToken) throws IOException {
        return getHttpPostResponse(path, body, loginToken).returnResponse();
    }

    private JsonElement bodyOf(final HttpResponse response) throws IOException {
        return PARSER.parse(responseMessage(response));
    }

    protected String responseMessage(final HttpResponse response) throws IOException {
        final InputStream content = response.getEntity().getContent();
        final StringWriter writer = new StringWriter();
        IOUtils.copy(content, writer, JsonEncoding.UTF8.getJavaName());
        return writer.toString();
    }

    protected String extractHeader(final String headerName, final HttpResponse response) {
        final Header[] headers = response.getHeaders(headerName);
        if (headers.length > 0) {
            return headers[0].getValue();
        } else {
            return null;
        }
    }

    /**
     * Porównanie odpowiedzi serewera z zadanym jsonem
     * UWAGA: metoda nie jest odporna na zmiany kolejności w kolekcjach!
     *
     * @param fileName - wzorcowy json
     * @param response - odpowieź serwera
     * @throws IOException w przypadku błędów IO
     */
    public void assertResponseEquals(final String fileName, final HttpResponse response) throws IOException {
        assertResponseEquals(fileName, bodyOf(response));
    }

    /**
     * Porównanie odpowiedzi serewera z zadanym plikiem tekstowym
     *
     * @param fileName - wzorcowy plik tekstowy
     * @param response - odpowieź serwera
     * @throws IOException w przypadku błędów IO
     */
    public void assertResponseEquals(final String fileName, final String response) throws IOException {
        assertEquals(getResponseFile("/messages/response/" + fileName), response);
    }

    /**
     * Porównanie odpowiedzi serewera z zadanym jsonem
     * UWAGA: metoda nie jest odporna na zmiany kolejności w kolekcjach!
     *
     * @param fileName - wzorcowy json
     * @param response - odpowieź serwera
     * @throws IOException w przypadku błędów IO
     */
    public void assertResponseEquals(final String fileName, final JsonElement response) throws IOException {
        final String jsonString = getResponseJson(response);
        try {
            final JsonElement expectedResponse = expectedResponse(fileName);
            assertEquals(expectedResponse, response);
        } catch (final AssertionError e) {
            writeJsonFile(fileName, jsonString);
            throw e;
        }
    }

    /**
     * Sprawdzenie odpowiedzi serwera ze spodziewaną odpowiedzią
     * Metoda srpawdza wartości na podstawie obiektów - jeśli jakaś kolekcja zachowuje kolejność, to jest ona sprawdzana
     *
     * @param fileName     - nazwa pliku json zawierającego spodziewaną odpowiedź
     * @param response     - odpowiedź serwera
     * @param responseType - typ obiektu
     * @throws IOException w przypadku błędów IO
     */
    protected void assertResponseEquals(final String fileName, final JsonElement response, final Type responseType) throws IOException {
        final String jsonString = getResponseJson(response);
        try {
            final JsonElement expectedResponse = expectedResponse(fileName);
            final Object responseObject = gson.fromJson(response, responseType);
            final Object expectedObject = gson.fromJson(expectedResponse, responseType);
            assertThat(responseObject).isEqualToComparingFieldByFieldRecursively(expectedObject);
        } catch (final JsonParseException | AssertionError e) {
            writeJsonFile(fileName, jsonString);
            throw e;
        }
    }

    /**
     * Sprawdzenie czy odpowiedź serwera nie jest taka sama jak zadana
     *
     * @param fileName - nazwa pliku json zawierającego odpowiedź, która nie powinna być taka sama jak odpowiedź z serwera
     * @param response - odpowiedź serwera
     * @param type     - typ obiektu
     * @throws IOException w przypadku błędów IO
     */
    protected void assertResponseNotEquals(final String fileName, final JsonElement response, final Type type) throws IOException {
        final JsonElement expectedResponse = expectedResponse(fileName);
        final Object responseObject = gson.fromJson(response, type);
        final Object expectedObject = gson.fromJson(expectedResponse, type);
        assertThat(responseObject).isNotEqualTo(expectedObject);
    }

    protected void assertEmptyCollectionResponse(final JsonElement response) throws IOException {
        assertEquals(expectedResponse("emptyCollection.json"), response);
    }

    private void writeJsonFile(final String fileName, final String responseJson) throws IOException {
        if (WRITE_JSON_TO_FILE_WHEN_ASSERT_ERROR_OCCURRED) {
            FileUtils.writeStringToFile(getFile(fileName), responseJson, UTF_8.name());
        }
    }

    /**
     * Budowanie parametrów zapytania http (extCompanyId=123&extCompanyId=124) dla podanej listy identyfikatorów
     *
     * @param extCompanyIds - lista identyfikatorów
     * @return parametry zapytania http
     */
    protected String extCompanyIdParams(final Long... extCompanyIds) {
        return Arrays.stream(extCompanyIds).map(Object::toString).collect(Collectors.joining("&extCompanyId="));
    }

    private String getResponseJson(final JsonElement response) {
        return new GsonBuilder().setPrettyPrinting().serializeNulls().create().toJson(response);
    }

    private class GsonDateTypeAdapter extends TypeAdapter<Date> {

        @Override
        public void write(final JsonWriter writer, final Date value) throws IOException {
            if (value == null) {
                writer.nullValue();
            } else {
                writer.value(value.getTime());
            }
        }

        @Override
        public Date read(final JsonReader reader) throws IOException {
            if (reader != null && reader.hasNext()) {
                final JsonToken peek = reader.peek();
                if (peek == JsonToken.NUMBER) {
                    return new Date(reader.nextLong());
                } else if (peek == JsonToken.NULL) {
                    reader.skipValue();
                    return null;
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }

    /**
     * Gson nie rzuca wyjątku jak json nie pasuje do obiektu na który ma zostać zmapowany.
     * Issue jest nadal otwarte https://github.com/google/gson/issues/188
     * Dlatego poniższa klasa to obejście tego problemu
     */
    private class ValidatorAdapterFactory implements TypeAdapterFactory {
        @Override
        public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
            final TypeAdapter<T> delegate = gson.getDelegateAdapter(this, type);
            if (delegate instanceof ReflectiveTypeAdapterFactory.Adapter) {
                try {
                    final Field f = delegate.getClass().getDeclaredField("boundFields");
                    f.setAccessible(true);
                    Map boundFields = (Map) f.get(delegate);
                    boundFields = new LinkedHashMap(boundFields) {

                        @Override
                        public Object get(final Object key) {
                            final Object value = super.get(key);
                            if (value == null) {
                                throw new JsonParseException("invalid property name: " + key);
                            }
                            return value;
                        }
                    };
                    f.set(delegate, boundFields);
                } catch (final Exception e) {
                    throw new IllegalStateException(e);
                }
            }
            return delegate;
        }
    }
}
