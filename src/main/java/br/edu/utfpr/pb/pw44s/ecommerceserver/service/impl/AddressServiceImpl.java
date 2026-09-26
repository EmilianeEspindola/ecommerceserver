package br.edu.utfpr.pb.pw44s.ecommerceserver.service.impl;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Address;
import br.edu.utfpr.pb.pw44s.ecommerceserver.repository.AddressRepository;
import br.edu.utfpr.pb.pw44s.ecommerceserver.service.IAddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements IAddressService {
    private final AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Address> findByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Address findByIdAndUserId(Long id, Long userId) {
        return addressRepository.findByIdAndUserId(id, userId);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        addressRepository.deleteById(id);
    }
}