public class UnderlinedLabelDecorator implements ComponentDecorator {

    @Override
    @SuppressWarnings("unchecked")
    public void decorate(Object component, Set<String> state) {
        DesktopLabel item = (DesktopLabel) component;
        JLabel jlabel = item.getComponent();

        Font originalFont = jlabel.getFont();
        Map attributes = originalFont.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        jlabel.setFont(originalFont.deriveFont(attributes));
    }
}