package bard.db.dictionary

/**
 * Created with IntelliJ IDEA.
 * User: dlahr
 * Date: 4/22/13
 * Time: 11:38 PM
 * To change this template use File | Settings | File Templates.
 */
public class ElementAndFullPath {
    private final pathDelimeter

    Element element

    /**
     * path from root of hierarchy to element
     */
    List<ElementHierarchy> path

    public ElementAndFullPath() {
        pathDelimeter = "\\"

        path = new LinkedList<ElementHierarchy>()
    }

    public ElementAndFullPath copy() {
        ElementAndFullPath copy = new ElementAndFullPath(element: element)

        copy.path.addAll(path)

        return copy
    }

    @Override
    String toString() {
        StringBuilder builder = new StringBuilder()

        for (ElementHierarchy eh : path) {
            builder.append(eh.parentElement.id).append(pathDelimeter)
        }
        builder.append(element.id)
    }
}
