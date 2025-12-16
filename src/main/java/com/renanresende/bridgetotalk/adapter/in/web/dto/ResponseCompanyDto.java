package com.renanresende.bridgetotalk.adapter.in.web.dto;

import java.util.UUID;

public record ResponseCompanyDto(
        UUID id,
        String name,
        String slug,
        String email,
        String phone,
        String document,
        CompanySettingsUpdateDto settings
) {

}
