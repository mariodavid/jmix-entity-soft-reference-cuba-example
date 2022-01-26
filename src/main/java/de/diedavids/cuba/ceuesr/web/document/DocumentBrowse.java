package de.diedavids.cuba.ceuesr.web.document;

import com.haulmont.cuba.gui.screen.*;
import de.diedavids.cuba.ceuesr.entity.Document;

import de.diedavids.jmix.softreference.cuba.SoftReferenceMigrationService;
import de.diedavids.jmix.softreference.screen.SoftReferenceInstanceNameTableColumnGenerator;
import groovy.lang.MetaProperty;
import io.jmix.core.MetadataTools;
import io.jmix.ui.Notifications;
import io.jmix.ui.ScreenBuilders;
import io.jmix.ui.UiComponents;
import io.jmix.ui.action.Action;
import io.jmix.ui.component.GroupTable;
import io.jmix.ui.screen.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

@UiController("ceuesr_Document.browse")
@UiDescriptor("document-browse.xml")
@LookupComponent("documentsTable")
@LoadDataBeforeShow
public class DocumentBrowse extends StandardLookup<Document> {

    @Inject
    protected GroupTable<Document> documentsTable;

    @Inject
    protected UiComponents uiComponents;

    @Inject
    protected MetadataTools metadataTools;

    @Inject
    protected ScreenBuilders screenBuilders;

    @Inject
    protected SoftReferenceMigrationService softReferenceMigrationService;
    @Autowired
    private Notifications notifications;
    @Autowired
    private MessageBundle messageBundle;

    @Subscribe
    protected void onInit(InitEvent event) {
        documentsTable.addGeneratedColumn("refersToInstanceName",
                new SoftReferenceInstanceNameTableColumnGenerator(
                        "refersTo",
                        uiComponents,
                        metadataTools,
                        screenBuilders,
                        this
                ));
    }

    @Subscribe("documentsTable.migrateToJmix")
    public void onDocumentsTableMigrateToJmix(Action.ActionPerformedEvent event) {

        int migratedAmount = softReferenceMigrationService.migrateSoftReferenceAttribute(
                Document.class,
                "refersTo",
                "refersToJmix",
                1,
                "createTs"
        );

        notifications.create(Notifications.NotificationType.TRAY)
                .withCaption(messageBundle.formatMessage("migrationSuccessful", migratedAmount))
                .show();

        getScreenData().loadAll();
    }
}
