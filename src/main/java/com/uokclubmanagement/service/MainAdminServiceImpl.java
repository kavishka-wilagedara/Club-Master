package com.uokclubmanagement.service;

import com.uokclubmanagement.entity.MainAdmin;
import com.uokclubmanagement.entity.Member;
import com.uokclubmanagement.repository.MainAdminRepository;
import com.uokclubmanagement.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MainAdminServiceImpl implements MainAdminService {

    @Autowired
    private MainAdminRepository mainAdminRepository;
    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Override
    public MainAdmin createMainAdmin(MainAdmin mainAdmin) {

        // Check username exists
        String username = mainAdmin.getMainAdminUsername();

        Optional<MainAdmin> optionalMainAdmin = Optional.ofNullable(mainAdminRepository.findMainAdminByMainAdminUsername(username));

        if (optionalMainAdmin.isPresent()) {
            throw new RuntimeException("username already exists");
        }

        // If not exist
        else{
        // Set adminId
        if (mainAdmin.getMainAdminId()   == null || mainAdmin.getMainAdminId().isEmpty()){
            long seqValue = sequenceGeneratorService.generateSequence("Main Admin Sequence");
            String mainAdminId = String.format("Adm-%04d", seqValue);
            mainAdmin.setMainAdminId(mainAdminId);

            }
            return mainAdminRepository.save(mainAdmin);
        }
    }

    @Override
    public List<MainAdmin> getAllMainAdmins() {
        return mainAdminRepository.findAll();
    }

    @Override
    public MainAdmin updateMainAdminById(String mainAdminID, MainAdmin mainAdmin) {

        Optional<MainAdmin> existingMainAdmin = mainAdminRepository.findById(mainAdminID);
        // Check if existingMainAdmin is null
        if (existingMainAdmin.isEmpty()) {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminID);
        }
        // Update the mainAdmin fields
        updateMainAdminFields(existingMainAdmin.get(), mainAdmin);

        // Save on mainAdmin collection
        return mainAdminRepository.save(existingMainAdmin.get());
    }

    private void updateMainAdminFields(MainAdmin existingMainAdmin, MainAdmin mainAdmin) {

        if (mainAdmin.getMainAdminName() != null) {
            existingMainAdmin.setMainAdminName(mainAdmin.getMainAdminName());
        }
        if (mainAdmin.getMainAdminEmail() != null) {
            existingMainAdmin.setMainAdminEmail(mainAdmin.getMainAdminEmail());
        }
        if (mainAdmin.getMainAdminPhone() != null) {
            existingMainAdmin.setMainAdminPhone(mainAdmin.getMainAdminPhone());
        }
        if (mainAdmin.getMainAdminPassword() != null) {
            existingMainAdmin.setMainAdminPassword(mainAdmin.getMainAdminPassword());
        }
    }

    @Override
    public void deleteMainAdminById(String mainAdminId) {

        Optional<MainAdmin> deleteMainAdmin = mainAdminRepository.findById(mainAdminId);
            if (deleteMainAdmin.isPresent()) {
                MainAdmin mainAdmin = deleteMainAdmin.get();
                System.out.println("Deleted mainAdmin: " + mainAdminId);
        }
        else {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminId);
        }
    }

    @Override
    public MainAdmin getMainAdminById(String mainAdminId) {

        Optional<MainAdmin> findMainAdmin = mainAdminRepository.findById(mainAdminId);
        if (findMainAdmin.isEmpty()) {
            throw new RuntimeException("MainAdmin not found with id: " + mainAdminId);
        }
        else {
            return findMainAdmin.get();
        }
    }
}
