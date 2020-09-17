// IMyAidlInterface.aidl
package com.example.ktapp;
import com.example.ktapp.data.db.User;
// Declare any non-default types here with import statements
interface IMyAidlInterface {
     User getUser(String name);
}
