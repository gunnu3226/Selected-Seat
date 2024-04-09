package io.nbc.selectedseat.web.domain.member.dto;

import org.springframework.web.multipart.MultipartFile;

public record UpdateMemberProfileRequestDTO(
    MultipartFile profile
) {

}
