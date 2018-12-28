package top.redstonemusic.toolpartstage;

import java.io.File;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.eventbus.Subscribe;

import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.GameStages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolCraftingEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolPartCraftingEvent;
import slimeknights.tconstruct.library.events.TinkerCraftingEvent.ToolPartReplaceEvent;
import slimeknights.tconstruct.library.utils.TinkerUtil;

@Mod(modid = ToolPartStage.MODID, name = ToolPartStage.NAME, version = ToolPartStage.VERSION, dependencies = "required-after:tconstruct;required-after:bookshelf;required-after:gamestages;required-after:crafttweaker")
@EventBusSubscriber
public class ToolPartStage {
	
	public static final String MODID = "toolpartstage";
	public static final String NAME = "Tool Part Stage";
	public static final String VERSION = "0.1-alpha";
	public static final File TOOL_PARTS_DUMP = new File(Minecraft.getMinecraft().mcDataDir, "ToolPartsDump.txt");
	public static final File MATERIALS_DUMP = new File(Minecraft.getMinecraft().mcDataDir, "MaterialsDump.txt");
	//tool part - material
	public static Multimap<String, String> TOOL_MAT_PAIR = HashMultimap.create();
	//combined tool part mat - stage
	public static Multimap<String, String> TOOL_PART_STAGE = HashMultimap.create();
	@EventHandler
    public void preInit(FMLPreInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(this);
		GameStages.COMMAND.addSubcommand(new DumpCommand());
    }
    
    @SubscribeEvent
    public void onCraftToolPart (ToolPartCraftingEvent event) {
    	if(TOOL_MAT_PAIR!= null && !TOOL_MAT_PAIR.isEmpty()) {
    		String toolPart = event.getItemStack().getUnlocalizedName().substring(event.getItemStack().getUnlocalizedName().lastIndexOf('.') + 1);
    		if(TOOL_PART_STAGE.containsKey(toolPart) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolPart))) {
    			event.setCanceled(I18n.format("msg.craft_tool_part.deny", event.getItemStack().getDisplayName()));
    		} else if(TOOL_MAT_PAIR.containsKey(toolPart)) {
    			String mat = TinkerUtil.getMaterialFromStack(event.getItemStack()).getIdentifier();
    			String toolMatPair = toolPart + ":" + mat;
    			if(TOOL_PART_STAGE.containsKey(toolMatPair) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolMatPair))) {
        			event.setCanceled(I18n.format("msg.craft_tool_part.deny", event.getItemStack().getDisplayName()));
        			
    			}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onReplaceToolPart (ToolPartReplaceEvent event) {
    	if(TOOL_MAT_PAIR!= null && !TOOL_MAT_PAIR.isEmpty()) {
    		for(ItemStack stack : event.getToolParts()) {
    			if(stack != null && !stack.isEmpty()) {
    				String toolPart = stack.getUnlocalizedName().substring(stack.getUnlocalizedName().lastIndexOf('.') + 1);
    				if(TOOL_PART_STAGE.containsKey(toolPart) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolPart))) {
    					System.out.println("Banned tool part!");
    					event.setCanceled(I18n.format("msg.replace_tool_part.deny", event.getItemStack().getDisplayName()));
        				break;
    				} else if(TOOL_MAT_PAIR.containsKey(toolPart)) {
    					String mat = TinkerUtil.getMaterialFromStack(stack).getIdentifier();
    					String toolMatPair = toolPart + ":" + mat;
    					if(TOOL_PART_STAGE.containsKey(toolMatPair) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolMatPair))) {
    						event.setCanceled(I18n.format("msg.replace_tool_part.deny", stack.getDisplayName()));
    						break;
    					}
    				}
        		}
    		}
    	}
    }
    
    @SubscribeEvent
    public void onToolCreated (ToolCraftingEvent event) {
    	if(TOOL_MAT_PAIR!= null && !TOOL_MAT_PAIR.isEmpty()) {
    		for(ItemStack stack : event.getToolParts()) {
    			if(stack != null && !stack.isEmpty()) {
    				String toolPart = stack.getUnlocalizedName().substring(stack.getUnlocalizedName().lastIndexOf('.') + 1);
    				if(TOOL_PART_STAGE.containsKey(toolPart) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolPart))) {
    					System.out.println("Banned tool part!");
    					event.setCanceled(I18n.format("msg.replace_tool_part.deny", event.getItemStack().getDisplayName()));
        				break;
    				} else if(TOOL_MAT_PAIR.containsKey(toolPart)) {
    					String mat = TinkerUtil.getMaterialFromStack(stack).getIdentifier();
    					String toolMatPair = toolPart + ":" + mat;
    					if(TOOL_PART_STAGE.containsKey(toolMatPair) && !GameStageHelper.hasAnyOf(event.getPlayer(), TOOL_PART_STAGE.get(toolMatPair))) {
    						event.setCanceled(I18n.format("msg.replace_tool_part.deny", stack.getDisplayName()));
    						break;
    					}
    				}
        		}
    		}
    	}
    }
}
