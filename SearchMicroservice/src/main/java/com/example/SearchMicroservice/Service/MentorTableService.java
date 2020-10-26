package com.example.SearchMicroservice.Service;

import com.example.SearchMicroservice.Entity.MentorTableEntity;

import java.util.Optional;

public interface MentorTableService {

    public MentorTableEntity findById(long id);
}
