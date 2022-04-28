package vuce2.mfeconfig.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vuce2.mfeconfig.dtos.*;
import vuce2.mfeconfig.services.ModuleContentGroupService;
import vuce2.library.common.model.StatusResponse;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/module-content-group")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
public class ModuleContentGroupController {

    @Resource
    private ModuleContentGroupService moduleContentGroupService;

    @PostMapping("/insert-content-group")
    public ResponseEntity<StatusResponse> Insert(@RequestBody ModuleContentGroupConfigDto request) {

        StatusResponse result = moduleContentGroupService.insertModuleContentGroup(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/insert-subModules")
    public ResponseEntity<StatusResponse> InsertSubModules(@RequestBody SubModulesDto request) {

        StatusResponse result = moduleContentGroupService.insertSubModules(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/insert-content")
    public ResponseEntity<StatusResponse> InsertContent(@RequestBody ModuleContentConfigDto request) {

        StatusResponse result = moduleContentGroupService.insertModuleContent(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/insert-item")
    public ResponseEntity<StatusResponse> InsertItem(@RequestBody ModuleContentItemConfigDto request) {

        StatusResponse result = moduleContentGroupService.insertModuleContentItem(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-content-group")
    public ResponseEntity<StatusResponse> UpdateContentGroup(@RequestBody ModuleContentGroupConfigDto request) {

        StatusResponse result = moduleContentGroupService.updateModuleContentGroup(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-subModule")
    public ResponseEntity<StatusResponse> UpdateSubModule(@RequestBody SubModulesDto request) {

        StatusResponse result = moduleContentGroupService.updateSubModule(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PutMapping("/update-content")
    public ResponseEntity<StatusResponse> UpdateContent(@RequestBody ModuleContentConfigDto request) {

        StatusResponse result = moduleContentGroupService.updateModuleContent(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/position-up-item")
    public ResponseEntity<StatusResponse> PositionUpItem(@RequestParam("id") String id) {

        StatusResponse result = moduleContentGroupService.positionUpModuleContentItem(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/position-down-item")
    public ResponseEntity<StatusResponse> PositionDownItem(@RequestParam("id") String id) {

        StatusResponse result = moduleContentGroupService.positionDownModuleContentItem(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PutMapping("/update-item")
    public ResponseEntity<StatusResponse> UpdateContentItem(@RequestBody ModuleContentItemConfigDto request) {

        StatusResponse result = moduleContentGroupService.updateModuleContentItem(request);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/delete-content-group")
    public ResponseEntity<?> deleteContentGroup(@RequestParam("id") String id) {
        StatusResponse result = moduleContentGroupService.deleteModuleContentGroup(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/delete-subModule")
    public ResponseEntity<?> deleteSubModule(@RequestParam("id") String id) {
        StatusResponse result = moduleContentGroupService.deleteSubModule(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/delete-content")
    public ResponseEntity<?> deleteContent(@RequestParam("id") String id) {
        StatusResponse result = moduleContentGroupService.deleteModuleContent(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @DeleteMapping("/delete-item")
    public ResponseEntity<?> deleteContentItem(@RequestParam("id") String id) {
        StatusResponse result = moduleContentGroupService.deleteModuleContentItem(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/get-content-group")
    public ResponseEntity<ModuleContentGroupConfigDto> readContentGroup(@RequestParam("id") String id) {
        ModuleContentGroupConfigDto result = moduleContentGroupService.readModuleContentGroup(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-content-group-by-user")
    public ResponseEntity<List<ModuleContentGroupConfigDto>> readContentGroupByUser(@RequestBody IdUserConfigDto userConfig) {
        List<ModuleContentGroupConfigDto> result = moduleContentGroupService.readModuleContentGroupByIdUser(userConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-subModule")
    public ResponseEntity<SubModulesDto> readSubModule(@RequestParam("id") String id) {
        SubModulesDto result = moduleContentGroupService.readSubModule(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-content")
    public ResponseEntity<ModuleContentConfigDto> readContent(@RequestParam("id") String id) {
        ModuleContentConfigDto result = moduleContentGroupService.readModuleContent(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-subModule-by-group")
    public ResponseEntity<List<SubModulesDto>> readSubModuleByGroup(@RequestParam("idGroup") String idGroup) {
        List<SubModulesDto> result = moduleContentGroupService.readSubModuleByGroup(idGroup);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-content-by-group")
    public ResponseEntity<List<ModuleContentConfigDto>> readContentByGroup(@RequestParam("idGroup") String idGroup) {
        List<ModuleContentConfigDto> result = moduleContentGroupService.readModuleContentByGroup(idGroup);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-item")
    public ResponseEntity<ModuleContentItemConfigDto> readContentItem(@RequestParam("id") String id) {
        ModuleContentItemConfigDto result = moduleContentGroupService.readModuleContentItem(id);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-item-by-content")
    public ResponseEntity<List<ModuleContentItemConfigDto>> readContentItemByContent(@RequestParam("idContent") String idContent) {
        List<ModuleContentItemConfigDto> result = moduleContentGroupService.readModuleContentItemByContent(idContent);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/get-content-group-all-by-user")
    public ResponseEntity<List<ModuleContentGroupIdStringDto>> readAll(@RequestBody IdUserConfigDto userConfig) {
        List<ModuleContentGroupIdStringDto> result = moduleContentGroupService.readContentAllGroupsByUser(userConfig);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
