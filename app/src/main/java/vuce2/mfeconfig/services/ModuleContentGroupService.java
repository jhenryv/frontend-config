package vuce2.mfeconfig.services;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import vuce2.mfeconfig.dtos.*;
import vuce2.mfeconfig.models.ModuleContent;
import vuce2.mfeconfig.models.ModuleContentGroup;
import vuce2.mfeconfig.models.ModuleContentItem;
import vuce2.mfeconfig.models.SubModules;
import vuce2.mfeconfig.repositories.ModuleContentGroupRepository;
import vuce2.library.common.model.StatusResponse;
import vuce2.library.common.util.ObjectMapperUtils;

import javax.annotation.Resource;
import java.util.*;

import static com.mongodb.client.model.Filters.*;

@Service
@Slf4j
public class ModuleContentGroupService {

    @Resource
    private MongoDatabase mongoDatabase;

    @Resource
    private ModuleContentGroupRepository moduleContentGroupRepository;


    public StatusResponse insertModuleContentGroup(ModuleContentGroupConfigDto request){
        StatusResponse sr = new StatusResponse();
        Optional<ModuleContentGroup> e = moduleContentGroupRepository.findByName(request.getName() );
        if(e.isEmpty()){
            ModuleContentGroup entity = ObjectMapperUtils.map(request, ModuleContentGroup.class);
            entity.setId(new ObjectId());
            entity.setContentDefault("");
            entity.setSubModules(new ArrayList<>());
            entity.setContents(new ArrayList<>());
            moduleContentGroupRepository.save(entity);
            sr.AddMessage("Content Group guardado correctamente");
            sr.setSuccess(true);
            return  sr;
        }
        sr.AddMessage("Content Group ya se encuentra registrado");
        return  sr;
    }
    public StatusResponse insertSubModules(SubModulesDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup", Document.class);
        Optional<ModuleContentGroup> r = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        if(r.isPresent()){
            var count = collection.countDocuments(and(elemMatch("subModules", eq("name", request.getName())),eq("name",request.getNameContentGroup())));
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(r.get().getId().toString()).get();
            if(count==0){
                SubModules entity = ObjectMapperUtils.map(request, SubModules.class);
                entity.setId(new ObjectId());
                moduleContentGroup.getSubModules().add(entity);
                moduleContentGroupRepository.save(moduleContentGroup);
                sr.setSuccess(true);
                sr.AddMessage("submodule guardado");
            }
            else sr.AddMessage("submodule ya se encuentra registrado");
            return sr;
        }
        sr.AddMessage("Content Group no existe");
        return sr;
    }
    public StatusResponse insertModuleContent(ModuleContentConfigDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup", Document.class);
        Optional<ModuleContentGroup> r = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        if(r.isPresent()){
            var count = collection.countDocuments(and(elemMatch("contents", eq("name", request.getName())),eq("name",request.getNameContentGroup())));
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(r.get().getId().toString()).get();
            if(count==0){
                if(r.get().getContentDefault().equals("")){
                    moduleContentGroup.setContentDefault(request.getName());
                }
                ModuleContent entity = ObjectMapperUtils.map(request, ModuleContent.class);
                entity.setId(new ObjectId());
                entity.setItems(new ArrayList<>());
                moduleContentGroup.getContents().add(entity);
                moduleContentGroupRepository.save(moduleContentGroup);
                sr.setSuccess(true);
                sr.AddMessage("content guardado");
            }
            else sr.AddMessage("Content ya se encuentra registrado");
            return sr;
        }
        sr.AddMessage("Content Group no existe");
        return sr;
    }
    public StatusResponse insertModuleContentItem(ModuleContentItemConfigDto request){
        StatusResponse sr = new StatusResponse();
        Optional<ModuleContentGroup> r = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        if(r.isPresent()){
            var count = collection.countDocuments(and(elemMatch("contents", eq("name", request.getNameContent())),eq("name",request.getNameContentGroup())));
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(r.get().getId().toString()).get();
            if(count == 1){
                var countItem = collection.countDocuments(and(eq("name",request.getNameContentGroup()),elemMatch("contents",and(elemMatch("items",and(eq("ngName",request.getNgName()),eq("remoteName",request.getRemoteName()),eq("componentName",request.getComponentName()))),eq("name",request.getNameContent())))));
                if(countItem==0){
                    for (ModuleContent item :moduleContentGroup.getContents()) {
                        if(item.getName().equals(request.getNameContent())){
                            ModuleContentItem entity = ObjectMapperUtils.map(request, ModuleContentItem.class);
                            entity.setId(new ObjectId());
                            item.getItems().add(entity);
                            moduleContentGroupRepository.save(moduleContentGroup);
                            sr.setSuccess(true);
                            sr.AddMessage("item guardado");
                            return sr;
                        }
                    }
                }
                else sr.AddMessage("Item ya se encuentra registrado");
                return sr;
            }
        }
        sr.AddMessage("Content Group no existe");
        return sr;
    }
    public StatusResponse updateModuleContentGroup(ModuleContentGroupConfigDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        Boolean exists = moduleContentGroupRepository.existsById(request.getId());
        if( exists){
            Optional<ModuleContentGroup> e = moduleContentGroupRepository.findById(request.getId());
            ModuleContentGroup entity = ObjectMapperUtils.map(e.get(), ModuleContentGroup.class);
            String name = entity.getName();
            entity.setName("");
            moduleContentGroupRepository.save(entity);
            var count = collection.countDocuments(eq("name",request.getName()));
            entity.setName(name);
            if(count==0){
                entity.setContentDefault(request.getContentDefault());
                entity.setId(request.getId());
                entity.setIdUser(request.getIdUser());
                entity.setName(request.getName());
                entity.setIcon(request.getIcon());
                entity.setUrl(request.getUrl());
                entity.setVisible(request.getVisible());
                entity.setContents(e.get().getContents());
                sr.AddMessage("Content Group actualizado correctamente");
                sr.setSuccess(true);

            }
            else sr.AddMessage("Content Group ya existe");
            moduleContentGroupRepository.save(entity);
            return  sr;
        }
        sr.AddMessage("Content Group no existe");
        return  sr;
    }

    public StatusResponse updateModuleContent(ModuleContentConfigDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        Optional<ModuleContentGroup> exists = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        if( exists.isPresent()){
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(exists.get().getId().toString()).get();
            ArrayList<ModuleContent> list = moduleContentGroup.getContents();
            for (ModuleContent item: list) {
                if(item.getId().toString().equals(request.getId())){
                    String name = item.getName();
                    item.setName("");
                    moduleContentGroupRepository.save(moduleContentGroup);
                    var count = collection.countDocuments(and(elemMatch("contents",eq("name",request.getName())),eq("name",request.getNameContentGroup())));
                    item.setName(name);
                    if(count==0){
                        if(moduleContentGroup.getContentDefault().equals(name)){
                            moduleContentGroup.setContentDefault(request.getName());
                        }
                        ModuleContent entity = ObjectMapperUtils.map(request, ModuleContent.class);
                        item.setName(entity.getName());
                        item.setId(entity.getId());
                        item.setTitle(entity.getTitle());
                        item.setSubTitle(entity.getSubTitle());
                        moduleContentGroupRepository.save(moduleContentGroup);
                        sr.setSuccess(true);
                        sr.AddMessage("content actualizado");
                    }
                    else sr.AddMessage("Content ya existe");
                    moduleContentGroupRepository.save(moduleContentGroup);
                    return  sr;
                }
            }
        }
        sr.AddMessage("content no existe");
        return  sr;
    }
    public StatusResponse updateSubModule(SubModulesDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        Optional<ModuleContentGroup> exists = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        if( exists.isPresent()){
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(exists.get().getId().toString()).get();
            ArrayList<SubModules> list = moduleContentGroup.getSubModules();
            for (SubModules item: list) {
                if(item.getId().toString().equals(request.getId())){
                    String name = item.getName();
                    item.setName("");
                    moduleContentGroupRepository.save(moduleContentGroup);
                    var count = collection.countDocuments(and(elemMatch("subModules",eq("name",request.getName())),eq("name",request.getNameContentGroup())));
                    item.setName(name);
                    if(count==0){
                        SubModules entity = ObjectMapperUtils.map(request, SubModules.class);
                        item.setName(entity.getName());
                        item.setIcon(entity.getIcon());
                        item.setContent(entity.getContent());
                        moduleContentGroupRepository.save(moduleContentGroup);
                        sr.setSuccess(true);
                        sr.AddMessage("SubModule actualizado");
                    }
                    else sr.AddMessage("SubModule ya existe");
                    moduleContentGroupRepository.save(moduleContentGroup);
                    return  sr;
                }
            }
        }
        sr.AddMessage("ModuleContentGroup no existe");
        return  sr;
    }
    public StatusResponse updateModuleContentItem(ModuleContentItemConfigDto request){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        Optional<ModuleContentGroup> exists = moduleContentGroupRepository.findByName(request.getNameContentGroup());
        if( exists.isPresent()){
            ModuleContentGroup moduleContentGroup = moduleContentGroupRepository.findById(exists.get().getId().toString()).get();
            ArrayList<ModuleContent> list = moduleContentGroup.getContents();
            for (ModuleContent item: list) {
                if(item.getName().equals(request.getNameContent())){
                    for (ModuleContentItem items: item.getItems()) {
                        if(items.getId().toString().equals(request.getId())){
                            String name = items.getNgName();
                            items.setNgName("");
                            moduleContentGroupRepository.save(moduleContentGroup);
                            var count = collection.countDocuments(and(eq("name",request.getNameContentGroup()),elemMatch("contents",and(elemMatch("items",and(eq("ngName",request.getNgName()),eq("remoteName",request.getRemoteName()),eq("componentName",request.getComponentName()))),eq("name",request.getNameContent())))));
                            items.setNgName(name);
                            if(count == 0){
                                ModuleContentItem entity = ObjectMapperUtils.map(request, ModuleContentItem.class);
                                items.setNgName(entity.getNgName());
                                items.setId(entity.getId());
                                items.setType(entity.getType());
                                items.setHidden(entity.getHidden());
                                items.setColMin(entity.getColMin());
                                items.setColMax(entity.getColMax());
                                items.setRowMin(entity.getRowMin());
                                items.setRowMax(entity.getRowMax());
                                items.setComponentName(entity.getComponentName());
                                items.setRemoteName(entity.getRemoteName());

                                sr.setSuccess(true);
                                sr.AddMessage("item actualizado");
                            }
                            else sr.AddMessage("item ya existe");
                            moduleContentGroupRepository.save(moduleContentGroup);
                            return  sr;
                        }
                    }
                }
            }
        }
        sr.AddMessage("content group no existe");
        return  sr;
    }
    public StatusResponse positionUpModuleContentItem(String id){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",elemMatch("items",eq("_id",objectId)))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            int count = 0;
            for (ModuleContentItem item: content.getItems()) {
                count ++;
                if(item.getId().toString().equals(id)){
                    if(count == 1){
                        sr.AddMessage("Item no se puede cambiar");
                        sr.setSuccess(true);
                        return sr;
                    }
                    else{
                        Collections.swap(content.getItems(),count-2,count-1);
                    }
                    moduleContentGroup.get().setContents(contentGroupConfigDto.getContents());
                    moduleContentGroupRepository.save(moduleContentGroup.get());
                    sr.AddMessage("Item cambiado correctamente");
                    sr.setSuccess(true);
                    return sr;
                }
            }
        }
        return sr;
    }
    public StatusResponse positionDownModuleContentItem(String id){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",elemMatch("items",eq("_id",objectId)))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            int count = 0;
            for (ModuleContentItem item: content.getItems()) {
                count ++;
                if(item.getId().toString().equals(id)){
                    if(count == content.getItems().size()){
                        sr.AddMessage("Item no se puede cambiar");
                        sr.setSuccess(true);
                        return sr;
                    }
                    else{
                        Collections.swap(content.getItems(),count-1,count);
                    }
                    moduleContentGroup.get().setContents(contentGroupConfigDto.getContents());
                    moduleContentGroupRepository.save(moduleContentGroup.get());
                    sr.AddMessage("Item cambiado correctamente");
                    sr.setSuccess(true);
                    return sr;
                }
            }
        }
        return sr;
    }
    public ModuleContentGroupConfigDto readModuleContentGroup(String id){
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(id);
        if(moduleContentGroup.isPresent()){
            ModuleContentGroupConfigDto contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroupConfigDto.class);
            return contentGroupConfigDto;
        }
        return null;
    }
    public ModuleContentConfigDto readModuleContent(String id){
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",eq("_id",objectId))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent item: contentGroupConfigDto.getContents()) {
            if(item.getId().toString().equals(id)){
                ModuleContentConfigDto response = ObjectMapperUtils.map(item, ModuleContentConfigDto.class);
                response.setNameContentGroup(contentGroupConfigDto.getName());
                return response;
            }
        }
        return null;
    }
    public SubModulesDto readSubModule(String id){
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("subModules",eq("_id",objectId))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (SubModules item: contentGroupConfigDto.getSubModules()) {
            if(item.getId().toString().equals(id)){
                SubModulesDto response = ObjectMapperUtils.map(item, SubModulesDto.class);
                response.setNameContentGroup(contentGroupConfigDto.getName());
                return response;
            }
        }
        return null;
    }
    public ModuleContentItemConfigDto readModuleContentItem(String id){
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",elemMatch("items",eq("_id",objectId)))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            for (ModuleContentItem item: content.getItems()) {
                if(item.getId().toString().equals(id)){
                    ModuleContentItemConfigDto response = ObjectMapperUtils.map(item, ModuleContentItemConfigDto.class);
                    response.setNameContentGroup(contentGroupConfigDto.getName());
                    response.setNameContent(content.getName());
                    return response;
                }
            }
        }
        return null;
    }
    public List<ModuleContentGroupIdStringDto> readContentAllGroupsByUser(IdUserConfigDto idUser){
        List<ModuleContentGroup> groups = moduleContentGroupRepository.findByIdUser(idUser.getIdUser());
        List<ModuleContentGroupIdStringDto> response = new ArrayList<>();
        for (ModuleContentGroup group: groups) {
            ModuleContentGroupIdStringDto moduleContentGroup = ObjectMapperUtils.map(group, ModuleContentGroupIdStringDto.class);
            if(moduleContentGroup.getSubModules()!=null) {
                for (SubModulesDto sub : moduleContentGroup.getSubModules()) {
                    sub.setNameContentGroup(group.getName());
                }
            }
            response.add(moduleContentGroup);
        }
        return response;
    }
    public List<ModuleContentGroupConfigDto> readModuleContentGroupByIdUser(IdUserConfigDto idUser){
        List<ModuleContentGroup> groups = moduleContentGroupRepository.findByIdUser(idUser.getIdUser());
        List<ModuleContentGroupConfigDto> response = new ArrayList<>();
        for (ModuleContentGroup group: groups) {
            ModuleContentGroupConfigDto contentGroupConfigDto = ObjectMapperUtils.map(group, ModuleContentGroupConfigDto.class);
            response.add(contentGroupConfigDto);
        }
        return response;
    }
    public List<SubModulesDto> readSubModuleByGroup(String idGroup){
        Optional<ModuleContentGroup> group = moduleContentGroupRepository.findById(idGroup);
        if(group.isPresent()){
            List<SubModules> subModules = group.get().getSubModules();
            List<SubModulesDto> response = new ArrayList<>();
            for (SubModules subModule: subModules) {
                SubModulesDto subModulesDto = ObjectMapperUtils.map(subModule, SubModulesDto.class);
                subModulesDto.setNameContentGroup(group.get().getName());
                response.add(subModulesDto);
            }
            return response;
        }
        return null;
    }
    public List<ModuleContentConfigDto> readModuleContentByGroup(String idGroup){
        Optional<ModuleContentGroup> group = moduleContentGroupRepository.findById(idGroup);
        if(group.isPresent()){
            List<ModuleContent> contents = group.get().getContents();
            List<ModuleContentConfigDto> response = new ArrayList<>();
            for (ModuleContent content: contents) {
                ModuleContentConfigDto contentGroupConfigDto = ObjectMapperUtils.map(content, ModuleContentConfigDto.class);
                contentGroupConfigDto.setNameContentGroup(group.get().getName());
                response.add(contentGroupConfigDto);
            }
            return response;
        }
        return null;
    }
    public List<ModuleContentItemConfigDto> readModuleContentItemByContent(String idContent){
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(idContent);
        var document = collection.find(elemMatch("contents",eq("_id",objectId))).first();
        var idGroup = document.getObjectId("_id").toString();
        List<ModuleContentItemConfigDto> response = new ArrayList<>();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            if(content.getId().toString().equals(idContent)){
                for (ModuleContentItem item: content.getItems()) {
                    ModuleContentItemConfigDto moduleContentItemConfigDto = ObjectMapperUtils.map(item, ModuleContentItemConfigDto.class);
                    moduleContentItemConfigDto.setNameContentGroup(moduleContentGroup.get().getName());
                    moduleContentItemConfigDto.setNameContent(content.getName());
                    response.add(moduleContentItemConfigDto);
                }
                return response;
            }
        }
        return null;
    }
    public StatusResponse deleteModuleContentGroup(String id){
        StatusResponse sr = new StatusResponse();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(id);
        if(moduleContentGroup.isPresent()){
            moduleContentGroupRepository.delete(moduleContentGroup.get());
            sr.AddMessage("Group eliminado correctamente");
            sr.setSuccess(true);
        }
        return sr;
    }
    public StatusResponse deleteSubModule(String id){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("subModules",eq("_id",objectId))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (SubModules subModules: contentGroupConfigDto.getSubModules()) {
            if(subModules.getId().toString().equals(id)){
                contentGroupConfigDto.getSubModules().remove(subModules);
                moduleContentGroup.get().setSubModules(contentGroupConfigDto.getSubModules());
                moduleContentGroupRepository.save(moduleContentGroup.get());
                sr.AddMessage("SubModule eliminado correctamente");
                sr.setSuccess(true);
                return sr;
            }
        }
        return sr;
    }
    public StatusResponse deleteModuleContent(String id){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",eq("_id",objectId))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            if(content.getId().toString().equals(id)){
                contentGroupConfigDto.getContents().remove(content);
                if(contentGroupConfigDto.getContentDefault().equals(content.getName())) {
                    if (contentGroupConfigDto.getContents().size() == 0) {
                        contentGroupConfigDto.setContentDefault("");
                    } else contentGroupConfigDto.setContentDefault(contentGroupConfigDto.getContents().get(0).getName());
                }
                moduleContentGroup.get().setContents(contentGroupConfigDto.getContents());
                moduleContentGroupRepository.save(moduleContentGroup.get());
                sr.AddMessage("Content eliminado correctamente");
                sr.setSuccess(true);
                return sr;
            }
        }
        return sr;
    }
    public StatusResponse deleteModuleContentItem(String id){
        StatusResponse sr = new StatusResponse();
        MongoCollection<Document> collection = mongoDatabase.getCollection("moduleContentGroup");
        ObjectId objectId = new ObjectId(id);
        var document = collection.find(elemMatch("contents",elemMatch("items",eq("_id",objectId)))).first();
        var idGroup = document.getObjectId("_id").toString();
        Optional<ModuleContentGroup> moduleContentGroup = moduleContentGroupRepository.findById(idGroup);
        ModuleContentGroup contentGroupConfigDto = ObjectMapperUtils.map(moduleContentGroup.get(), ModuleContentGroup.class);
        for (ModuleContent content: contentGroupConfigDto.getContents()) {
            for (ModuleContentItem item: content.getItems()) {
                if(item.getId().toString().equals(id)){
                    content.getItems().remove(item);
                    moduleContentGroup.get().setContents(contentGroupConfigDto.getContents());
                    moduleContentGroupRepository.save(moduleContentGroup.get());
                    sr.AddMessage("Item eliminado correctamente");
                    sr.setSuccess(true);
                    return sr;
                }
            }
        }
        return sr;
    }
}
