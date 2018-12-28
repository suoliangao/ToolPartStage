package top.redstonemusic.toolpartstage;

import crafttweaker.annotations.ZenRegister;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;

@ZenRegister
@ZenClass("mods.ToolPartStage")
public class CTcompact {
	
	@ZenMethod
	public static void addToolPartStage (String stage, String toolPart, @Optional String material) {
		ToolPartStage.TOOL_MAT_PAIR.put(toolPart, material);
		String combineToolMat = material == null ? toolPart : toolPart + ":" + material;
		ToolPartStage.TOOL_PART_STAGE.put(combineToolMat, stage);
	}
	
	@ZenMethod
	public static void addToolPartStage (String stage, String toolPart, String[] materials) {
		for(String mat : materials) {
			addToolPartStage(stage, toolPart, mat);
		}
	}
	
	@ZenMethod
	public static void addToolPartStage (String stage, String[] toolParts, @Optional String material) {
		for(String part : toolParts) {
			addToolPartStage(stage, part, material);
		}
	}
	
	@ZenMethod
	public static void addToolPartStage (String stage, String[] toolParts, String[] materials) {
		for(String part : toolParts) {
			for(String mat : materials) {
				addToolPartStage(stage, part, mat);
			}
		}
	}
	
}
