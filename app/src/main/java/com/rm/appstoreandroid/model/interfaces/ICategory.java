package com.rm.appstoreandroid.model.interfaces;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;

import com.rm.appstoreandroid.model.dto.CategoryDTO;
import com.rm.appstoreandroid.model.utils.Callbacks;
import com.rm.appstoreandroid.persistence.database_helper.DatabaseHelper;

import java.util.List;

/**
 * Created by oscargallon on 5/5/16.
 */
public interface ICategory {

    /**
     * Interfaz para obtener una lista de categorias desde un cursor
     * @param cursor cursor que apunta a la tabla
     * @return lista o nulo si error
     */
    List<CategoryDTO> createCategoriesFromCursor(Cursor cursor);

    /**
     * Metodo para obtener las categorias desde un array definido en los recursos
     * @param context contexto
     * @param getCategoiesFromArrayResourcesCallback interfaz para llamar
     *                                               el callback de exito o error
     */
    void getCategoriesFromResourcesArray(Context context,
                                         Callbacks.GetCategoiesFromArrayResourcesCallback
                                                 getCategoiesFromArrayResourcesCallback);

    /**
     * Metodo para guardar las categorias en la base de datos
     * @param sqLiteDatabase base de datos
     * @param categoryDTOList lista de categorias a guardar
     * @return arreglo de enteros con numero de operaciones exitosas y numero operaciones fallidas
     */
    int[] saveCategoriesIntoDatabase(SQLiteDatabase sqLiteDatabase, List<CategoryDTO> categoryDTOList);

    /**
     * Metodo para obtener una lista de content values desde una lista de categorias
     * @param categoryDTOList categorias
     * @return lista de content values
     */
    List<ContentValues> getContentValuesFromCategoriesDTOList(List<CategoryDTO> categoryDTOList);
}
