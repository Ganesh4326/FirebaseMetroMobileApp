package com.example.firebasemymetro.models;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DaoEmployee {
    private DatabaseReference databaseReference;

    public DaoEmployee() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Employee.class.getSimpleName());
    }

    public Task<Void> add(Employee emp) {
        return databaseReference.push().setValue(emp);
    }

    public Task<Void> addEmp(Employee employee){
        return databaseReference.push().setValue(employee);
    }
}
