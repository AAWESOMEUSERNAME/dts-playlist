package com.gugu.dts.playlist.ui.dto;

import com.gugu.dts.playlist.api.object.IFilter;
import com.gugu.dts.playlist.api.object.IFilterGroupDTO;
import com.gugu.dts.playlist.api.object.IRuleDTO;
import kotlin.Pair;
import lombok.Builder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author chenyiyang
 * @date 2020/10/26
 */
@Builder
public class RuleDTO implements IRuleDTO {
    private List<Pair<Integer, IFilterGroupDTO>> filterGroups;
    private int total;
    private boolean fairlyMode;


    @NotNull
    @Override
    public List<Pair<Integer, IFilterGroupDTO>> getFilterGroups() {
        return filterGroups;
    }

    @Override
    public int getTotal() {
        return total;
    }

    @Override
    public boolean getFairlyMode() {
        return fairlyMode;
    }

    @Builder
    public static class FilterGroupDTO implements IFilterGroupDTO {
        private List<IFilter> filters;
        private boolean logic;

        @NotNull
        @Override
        public List<IFilter> getFilters() {
            return filters;
        }

        @Override
        public boolean getLogic() {
            return logic;
        }
    }

}
