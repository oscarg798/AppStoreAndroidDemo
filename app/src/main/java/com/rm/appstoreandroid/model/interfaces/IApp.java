package com.rm.appstoreandroid.model.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.App;
import com.rm.appstoreandroid.model.dto.AppDTO;
import com.rm.appstoreandroid.model.dto.ImageDTO;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by oscargallon on 5/10/16.
 */
public interface IApp {

    /**
     * Interfas para guardar o actualizar las aplicacion
     * @param sqLiteDatabase base de datos
     * @param appDTOList lista de categorias
     * @return arreglo con el numero de celdas impactadas positivamente en la posicion 0
     *          y cuando no se efectuo en la posicion 1
     */
    int[] saveOrUpdateIntoDatabase(SQLiteDatabase sqLiteDatabase, List<AppDTO> appDTOList);

    /**
     * Interfaz para obtener una lista de aplicaciones desde un json
     * @param jsonArray arreglo json
     * @param context contexto
     * @return lista o nulo si ocurre un error
     */
    List<AppDTO> getAppFromJsonArray(JSONArray jsonArray, Context context);

    /**
     * Interfaz para obtener un aplicacion desde un objeto json
     * @param jsonObject objeto json
     * @param context contexto
     * @return aplicacion o nulo si ocurre un error
     */
    AppDTO getAppFromJsonObject(JSONObject jsonObject, Context context);

    /**
     * Interfaz para obtener una lista de aplicaciones desde la base de datos
     * segun la categoria
     * @param db base de datos
     * @param term categoria
     * @return lista de aplicaciones, nulo si error
     */
    List<AppDTO> getAppsDTOByCategoryTerm(SQLiteDatabase db, String term);

    /**
     * Interfaz obtener una lista de aplicaciones  desde la base de datos
     * @param cursor cursor que apunta a la tabla
     * @param db base de datos
     * @return lista de aplicaciones nulo si error 
     */
    List<AppDTO> getAPPDTOFromCursor(Cursor cursor, SQLiteDatabase db);




}
