package me.sxmurai.observant.util.internal;

import me.sxmurai.observant.Observant;

public interface Wrapper {
    default Observant getAC() {
        return Observant.getInstance();
    }
}
