package com.controller;

import com.entities.Delivery;
import com.controller.util.JsfUtil;
import com.controller.util.PaginationHelper;
import com.entities.UserHasGroup;
import com.entities.Work;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.io.Serializable;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.model.SelectItem;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@Named("deliveryController")
@SessionScoped
public class DeliveryController implements Serializable {

    private Delivery current;
    private DataModel items = null;
    @EJB
    private com.controller.DeliveryFacade ejbFacade;
    private PaginationHelper pagination;
    private int selectedItemIndex;
    private Part file;

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public DeliveryController() {
    }

    public Delivery getSelected() {
        if (current == null) {
            current = new Delivery();
            selectedItemIndex = -1;
        }
        return current;
    }

    private DeliveryFacade getFacade() {
        return ejbFacade;
    }

    public PaginationHelper getPagination() {
        if (pagination == null) {
            pagination = new PaginationHelper(10) {

                @Override
                public int getItemsCount() {
                    return getFacade().count();
                }

                @Override
                public DataModel createPageDataModel() {
                    return new ListDataModel(getFacade().findRange(new int[]{getPageFirstItem(), getPageFirstItem() + getPageSize()}));
                }
            };
        }
        return pagination;
    }

    public String prepareList() {
        recreateModel();
        return "List";
    }

    public String prepareView() {
        current = (Delivery) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "View";
    }

    public String prepareCreate() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        String workId = request.getParameter("workId");

        List<UserHasGroup> list = getFacade().findByGroupUser();
        if (!list.get(0).getDeliveryCollection().isEmpty()) {
            JsfUtil.addErrorMessage("Ya entrego la asignación");
            return null;
        }

        List<Work> works = getFacade().findById(workId);
        Work work = works.get(0);

        current = new Delivery();
        current.setFkIdWork(work);
        current.setDescription(" ");
        selectedItemIndex = -1;
        return "/crud/delivery/Create";
    }

    public String create() {
        try {
            InputStream input = file.getInputStream();
            String fileName = file.getSubmittedFileName();
            File copies = new File("C:\\Users\\presa\\Documents\\NetBeansProjects\\ControlGroup\\controlGroup\\web", fileName);
            Files.copy(input, copies.toPath());
            current.setFileUrl(copies.toPath().toString());
            List<UserHasGroup> uhg = getFacade().findByGroupUser();
            current.setFkIdUserHasGroup(uhg.get(0));
            if (current.getDescription() == null) {
                current.setDescription("");
            }
            current.setCreatedAt(new Date());
            getFacade().create(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DeliveryCreated"));
            return prepareCreate();
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, e.getMessage());
            return null;
        }
    }

    public static String getFileNameFromPart(Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : partHeader.split(";")) {
            if (content.trim().startsWith("filename")) {
                String fileName = content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
                return fileName;
            }
        }
        return null;
    }

    public void downloadFile(String path) throws IOException {
        // Get the FacesContext
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        File file = new File(path);
        String fileName = file.getName();
        String contentType = ec.getMimeType(fileName); // JSF 1.x: ((ServletContext) ec.getContext()).getMimeType(fileName);
        int contentLength = (int) file.length();

        // seta cabeçalhos http
        ec.responseReset(); // limpa qualquer possível sujeira feita por algum filtro ou componente
        ec.setResponseContentType(contentType); // tipo do conteúdo, como "application/pdf" por exemplo
        ec.setResponseContentLength(contentLength); // opcional. ajuda o browser com a barra de progresso
        ec.setResponseHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\""); // diz pro navegador para fazer download (attachment) em vez de renderizar diretamente na página (inline)
        try {
            // escreve arquivo no fluxo de saida
            OutputStream output = ec.getResponseOutputStream();
        } catch (IOException ex) {
            Logger.getLogger(DeliveryController.class.getName()).log(Level.SEVERE, null, ex);
        }
        // finaliza ciclo de vida
        fc.responseComplete(); // importante! Se você esquecer isso o jsf tentará escrever o response também e um erro ocorrerá
    }

    public String prepareEdit() {
        current = (Delivery) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        return "Edit";
    }

    public String update() {
        try {
            getFacade().edit(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DeliveryUpdated"));
            return "View";
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            return null;
        }
    }

    public String destroy() {
        current = (Delivery) getItems().getRowData();
        selectedItemIndex = pagination.getPageFirstItem() + getItems().getRowIndex();
        performDestroy();
        recreatePagination();
        recreateModel();
        return "List";
    }

    public String destroyAndView() {
        performDestroy();
        recreateModel();
        updateCurrentItem();
        if (selectedItemIndex >= 0) {
            return "View";
        } else {
            // all items were removed - go back to list
            recreateModel();
            return "List";
        }
    }

    private void performDestroy() {
        try {
            getFacade().remove(current);
            JsfUtil.addSuccessMessage(ResourceBundle.getBundle("/Bundle").getString("DeliveryDeleted"));
        } catch (Exception e) {
            JsfUtil.addErrorMessage(e, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
        }
    }

    private void updateCurrentItem() {
        int count = getFacade().count();
        if (selectedItemIndex >= count) {
            // selected index cannot be bigger than number of items:
            selectedItemIndex = count - 1;
            // go to previous page if last page disappeared:
            if (pagination.getPageFirstItem() >= count) {
                pagination.previousPage();
            }
        }
        if (selectedItemIndex >= 0) {
            current = getFacade().findRange(new int[]{selectedItemIndex, selectedItemIndex + 1}).get(0);
        }
    }

    public DataModel getItems() {
        if (items == null) {
            items = getPagination().createPageDataModel();
        }
        return items;
    }

    private void recreateModel() {
        items = null;
    }

    private void recreatePagination() {
        pagination = null;
    }

    public String next() {
        getPagination().nextPage();
        recreateModel();
        return "List";
    }

    public String previous() {
        getPagination().previousPage();
        recreateModel();
        return "List";
    }

    public SelectItem[] getItemsAvailableSelectMany() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), false);
    }

    public SelectItem[] getItemsAvailableSelectOne() {
        return JsfUtil.getSelectItems(ejbFacade.findAll(), true);
    }

    public Delivery getDelivery(java.lang.Integer id) {
        return ejbFacade.find(id);
    }

    @FacesConverter(forClass = Delivery.class)
    public static class DeliveryControllerConverter implements Converter {

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            DeliveryController controller = (DeliveryController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "deliveryController");
            return controller.getDelivery(getKey(value));
        }

        java.lang.Integer getKey(String value) {
            java.lang.Integer key;
            key = Integer.valueOf(value);
            return key;
        }

        String getStringKey(java.lang.Integer value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value);
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Delivery) {
                Delivery o = (Delivery) object;
                return getStringKey(o.getId());
            } else {
                throw new IllegalArgumentException("object " + object + " is of type " + object.getClass().getName() + "; expected type: " + Delivery.class.getName());
            }
        }

    }

}
