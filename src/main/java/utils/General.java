package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class General {
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
}
