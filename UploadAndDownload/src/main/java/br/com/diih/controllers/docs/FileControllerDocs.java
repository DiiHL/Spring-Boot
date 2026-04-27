package br.com.diih.controllers.docs;

import br.com.diih.data.dto.v1.UploadFileResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Tag(name = "File Storage", description = "Endpoints for file upload and download")
public interface FileControllerDocs {

    @Operation(summary = "Upload a file", description = "Upload a single file to server",
            tags = {"File"},
            responses = {
                    @ApiResponse(description = "File uploaded successfully", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server error", responseCode = "500")
            })
    UploadFileResponseDTO uploadFile(MultipartFile file);

    @Operation(summary = "Upload Multiple File", description = "Upload multiple file to server",
            tags = {"File"},
            responses = {
                    @ApiResponse(description = "File uploaded succesfully", responseCode = "200"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server error", responseCode = "500")
            }
    )
    List<UploadFileResponseDTO> uploadMultipleFile(MultipartFile[] files);

    @Operation(summary = "Download a file", description = "Download a file the server",
            tags = {"File"},
            responses = {
                    @ApiResponse(description = "File downloaded successfully", responseCode = "200", content = {
                                        @Content(schema = @Schema(implementation = UploadFileResponseDTO.class))}),

                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Unauthorized", responseCode = "401"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Internal Server error", responseCode = "500")
            }
    )
    ResponseEntity<Resource> downloadFile(String fileName, HttpServletRequest request);
}
