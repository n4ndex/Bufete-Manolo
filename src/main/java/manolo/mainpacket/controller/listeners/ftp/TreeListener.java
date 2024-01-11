
/**
 * TreeListener - TreeSelectionListener for handling tree selection events in the FTPWindow of the MainPacket application.
 *
 * This class listens for tree selection events and updates the FTPWindow based on the selected tree node.
 */

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

            String nodeName = selectedNode.getUserObject().toString();
            mainController.getFtpWindow().getRenameField().setText(nodeName);


            // Si es un directorio, elimina el primer carácter ("/")
            if (selectedNode.isLeaf()) {    // file
                String selectedPathString = getSelectedPath(selectedPath);

                mainController.getFtpWindow().setDirectory(selectedPathString.substring(1));
            } else {    // directory
                mainController.getFtpWindow().setDirectory(nodeName);
            }

        }
    }

    public String getSelectedPath(TreePath selectedPath) {
        int pathCount = selectedPath.getPathCount();

        Object penultimatePathComponent = selectedPath.getPathComponent(pathCount - 2);
        return penultimatePathComponent.toString();
    }
}
