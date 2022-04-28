package vuce2.mfeconfig.controllers;

import org.springframework.web.multipart.MultipartFile;
import vuce2.library.common.model.StatusResponse;

public abstract class BaseController {

    protected StatusResponse VerifyUploadedFile(MultipartFile file) {
        var result = new StatusResponse();
        if (file == null || file.isEmpty()) {
            result.AddMessage("Archivo vacío.");
            return result;
        }
        String fileContentType = file.getContentType();
        if (fileContentType.startsWith("text/") ||
                fileContentType.endsWith("/json")) {
        } else {
            result.AddMessage("Tipo de archivo no válido.");
            return result;
        }
        result.setSuccess(true);
        return result;
    }

}
