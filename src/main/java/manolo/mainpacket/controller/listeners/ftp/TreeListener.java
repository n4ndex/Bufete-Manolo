package manolo.mainpacket.controller.listeners.ftp;

import manolo.mainpacket.controller.MainController;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class TreeListener implements TreeSelectionListener {

    private final MainController mainController;

    public TreeListener(MainController mainController) {
        this.mainController = mainController;
    }

    /**
     * Called whenever the value of the selection changes.
     *
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        TreePath selectedPath = mainController.getFtpWindow().getTreeDirectories().getSelectionPath();

        if (selectedPath != null) {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();

            // Verifica si el nodo seleccionado es una hoja (archivo)
            if (selectedNode.isLeaf()) {
                String nodeName = selectedNode.getUserObject().toString();
                mainController.getFtpWindow().getRenameField().setText(nodeName);

                String selectedDirectory = getSelectedDirectoryPath(selectedNode);

                mainController.getFtpWindow().setDirectory(selectedDirectory);
                mainController.getFtpWindow().getRutaLabel().setText(mainController.getFtpWindow().getModel().getTextsList().get(1) + mainController.getFtpWindow().getDirectory());
            }
        }
    }

    public String getSelectedDirectoryPath(DefaultMutableTreeNode selectedNode) {
        TreePath path = new TreePath(selectedNode.getPath());
        int pathCount = path.getPathCount();

        Object penultimatePathComponent = path.getPathComponent(pathCount - 2);
        return penultimatePathComponent.toString();
    }
}
