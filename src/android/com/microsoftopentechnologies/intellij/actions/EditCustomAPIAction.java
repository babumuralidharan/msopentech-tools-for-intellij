/**
 * Copyright 2014 Microsoft Open Technologies Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.microsoftopentechnologies.intellij.actions;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.microsoftopentechnologies.intellij.forms.CustomAPIForm;
import com.microsoftopentechnologies.intellij.helpers.UIHelper;
import com.microsoftopentechnologies.intellij.model.CustomAPI;
import com.microsoftopentechnologies.intellij.model.MobileServiceTreeItem;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.util.UUID;


public class EditCustomAPIAction extends AnAction {
    public void update(AnActionEvent event) {
        PropertiesComponent pc = PropertiesComponent.getInstance(event.getProject());
        boolean enabled = Boolean.parseBoolean(pc.getValue("pluginenabled"));
        event.getPresentation().setEnabled(enabled);

        if(enabled) {
            MobileServiceTreeItem item = getSelectedScript(event);
            event.getPresentation().setVisible(item != null);
        }

    }

    private MobileServiceTreeItem getSelectedScript(AnActionEvent event) {
        JTree tree = UIHelper.getProjectTree();

        if(tree != null) {
        TreePath tp = tree.getLeadSelectionPath();
            if(tp != null && tp.getLastPathComponent() instanceof DefaultMutableTreeNode){
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
                if(selectedNode != null) {
                    Object selectedObject = selectedNode.getUserObject();
                    if (selectedObject instanceof CustomAPI)
                        return (MobileServiceTreeItem) selectedObject;
                }
            }
        }

        return null;
    }
    
    //OSH++ Test Commit
    private MobileServiceTreeItem1 getSelectedScript(AnActionEvent event) {
        JTree tree = UIHelper.getProjectTree();

        if(tree != null) {
        TreePath tp = tree.getLeadSelectionPath();
            if(tp != null && tp.getLastPathComponent() instanceof DefaultMutableTreeNode){
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
                if(selectedNode != null) {
                    Object selectedObject = selectedNode.getUserObject();
                    if (selectedObject instanceof CustomAPI)
                        return (MobileServiceTreeItem1) selectedObject;
                }
            }
        }

        return null;
    }

    public void actionPerformed(AnActionEvent e) {
        PropertiesComponent pc = PropertiesComponent.getInstance(e.getProject());
        String serviceName = pc.getValue("serviceName");
        String subscriptionId = pc.getValue("subscriptionId");

        JTree tree = UIHelper.getProjectTree();

        CustomAPI selectedApi = null;
        TreePath tp = tree.getLeadSelectionPath();

        if(tp != null && tp.getLastPathComponent() instanceof DefaultMutableTreeNode){
            final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tp.getLastPathComponent();
            Object selectedObject = selectedNode.getUserObject();
            if(selectedObject instanceof CustomAPI)
                selectedApi = (CustomAPI) selectedObject;


            final CustomAPIForm form = new CustomAPIForm();
            form.setEditingCustomAPI(selectedApi);
            form.setServiceName(serviceName);
            form.setAfterSave(new Runnable() {
                @Override
                public void run() {
                    selectedNode.setUserObject(form.getEditingCustomAPI());
                }
            });
            form.setSubscriptionId(UUID.fromString(subscriptionId));
            form.setProject(e.getProject());
            UIHelper.packAndCenterJDialog(form);
            form.setVisible(true);
        }
    }
}
