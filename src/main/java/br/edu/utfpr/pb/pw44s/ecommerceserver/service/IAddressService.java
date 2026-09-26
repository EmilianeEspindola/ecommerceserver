package br.edu.utfpr.pb.pw44s.ecommerceserver.service;

import br.edu.utfpr.pb.pw44s.ecommerceserver.model.Address;

import java.util.List;

public interface IAddressService {
    Address save(Address address);

    List<Address> findByUserId(Long userId);

    Address findByIdAndUserId(Long id, Long userId);

    void delete(Long id);
}