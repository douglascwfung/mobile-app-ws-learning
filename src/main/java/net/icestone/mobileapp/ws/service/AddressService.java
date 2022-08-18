package net.icestone.mobileapp.ws.service;

import java.util.List;

import net.icestone.mobileapp.ws.shared.dto.AddressDTO;

public interface AddressService {
	
	List<AddressDTO> getAddresses(String userID);
	AddressDTO getAddress(String addressId);

}

