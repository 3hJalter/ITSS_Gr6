package utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * The JsonFunction class provides utility methods for converting data structures
 * to JSON format.
 */
public class JsonFunction {
    /**
     * Converts a ResultSet object to a JSONArray containing the result set data in JSON format.
     *
     * @param resultSet The ResultSet object to be converted.
     * @return A JSONArray representing the converted result set data in JSON format.
     * @throws SQLException If a database access error occurs.
     * @throws JSONException If there is an error during JSON object construction.
     */
    public static JSONArray convertResultSetToJsonArray(ResultSet resultSet) throws SQLException, JSONException {
        JSONArray jsonArray = new JSONArray();

        ResultSetMetaData metaData = resultSet.getMetaData();
        int columnCount = metaData.getColumnCount();

        while (resultSet.next()) {
            JSONObject jsonObject = new JSONObject();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = metaData.getColumnName(i);
                Object value = resultSet.getObject(i);
                jsonObject.put(columnName, value);
            }
            jsonArray.put(jsonObject);
        }

        return jsonArray;
    }

    /**
     * Converts an object to its JSON representation.
     *
     * @param object The object to be converted to JSON.
     * @return A String containing the JSON representation of the input object.
     * @throws RuntimeException If there is an error during JSON serialization.
     */
    public static String convertToJson(Object object) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json;
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return json;
    }

}
