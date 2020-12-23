package com.shark.room.database.help;

import androidx.room.ColumnInfo;

/**
 * @author Darren(Zeng Dongyang)
 * @date 2019-07-06
 */
public class NameTuple {
    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;
}