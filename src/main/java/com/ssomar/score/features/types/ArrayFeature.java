package com.ssomar.score.features.types;

import com.ssomar.score.features.FeatureAbstract;
import com.ssomar.score.features.FeatureParentInterface;
import com.ssomar.score.features.FeatureSettingsInterface;
import com.ssomar.score.menu.GUI;
import com.ssomar.score.splugin.SPlugin;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArrayFeature<X> extends FeatureAbstract<X [], ArrayFeature<X>> {

    private X[] value;
    private int maxArraySize;

    public ArrayFeature(FeatureParentInterface parent, FeatureSettingsInterface featureSettings, int maxArraySize) {
        super(parent, featureSettings);
        this.maxArraySize = maxArraySize;
        reset();
    }

    public static ArrayFeature buildNull() {
        return new ArrayFeature(null, null, 100);
    }

    @Override
    public List<String> load(SPlugin plugin, ConfigurationSection config, boolean isPremiumLoading) {
        List<String> errors = new ArrayList<>();
        List<X> valueStr = (List<X>) config.getList(this.getName());
        if (valueStr != null) {
            this.value = (X[]) new Object[valueStr.size()];
            for (int i = 0; i < valueStr.size(); i++) {
                this.value[i] = valueStr.get(i);
                //SsomarDev.testMsg("LOADING "+this.getName()+" "+valueStr.get(i).getClass(), true);
            }

        } else {
            errors.add("&cThe feature "+this.getName()+" is not a list of object");
        }


        return errors;
    }

    @Override
    public void save(ConfigurationSection config) {
        List<X> valueStr = new ArrayList<>();
        for (X x : value) {
            valueStr.add(x);
        }
        config.set(this.getName(), valueStr);
    }

    // it will cause issue  https://stackoverflow.com/questions/27591061/ljava-lang-object-cannot-be-cast-to-ljava-lang-integer
    @Override
    public X[] getValue() {
       return value;
    }

    public X getArrayValueAtIndex(int index) {
        return (X) value[index];
    }

    @Override
    public ArrayFeature initItemParentEditor(GUI gui, int slot) {
        String[] finalDescription = new String[getEditorDescription().length + 2];
        System.arraycopy(getEditorDescription(), 0, finalDescription, 0, getEditorDescription().length);
        if (!isPremium() && this.isRequirePremium()) {
            finalDescription[finalDescription.length - 2] = GUI.PREMIUM;
        } else finalDescription[finalDescription.length - 2] = GUI.CLICK_HERE_TO_CHANGE;
        finalDescription[finalDescription.length - 1] = "&7Currently: ";

        gui.createItem(getEditorMaterial(), 1, slot, GUI.TITLE_COLOR + getEditorName(), false, false, finalDescription);
        return this;
    }

    @Override
    public void updateItemParentEditor(GUI gui) {

    }

    @Override
    public ArrayFeature clone(FeatureParentInterface newParent) {
        ArrayFeature clone = new ArrayFeature(newParent, getFeatureSettings(), getMaxArraySize());
        clone.setValue(value);
        return clone;
    }

    @Override
    public void reset() {
        this.value = (X[]) new Object[100];
    }
}
