package top.redstonemusic.toolpartstage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import crafttweaker.mc1120.commands.SpecialMessagesChat;
import net.darkhax.bookshelf.command.Command;
import net.minecraft.client.resources.I18n;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import slimeknights.tconstruct.library.TinkerRegistry;
import slimeknights.tconstruct.library.materials.Material;
import slimeknights.tconstruct.library.tools.IToolPart;
import slimeknights.tconstruct.library.tools.ToolPart;

public class DumpCommand extends Command{

	public static final String textFormatCode = "\u00A7";
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "dump";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
//		return "Added by Tool Part Stage\n"
//				+ "dump toolpart(part)|material(mat)\n"
//				+ "- toolpart: Dump all registered tool parts.\n"
//				+ "- material: Dump all registered tinker materials.";
		return I18n.format(textFormatCode + "c" + "msg.cmdusage.line1") + "\n" +
				I18n.format(textFormatCode + "c" + "msg.cmdusage.line2") + "\n" +
				I18n.format(textFormatCode + "c" + "msg.cmdusage.line3") + "\n" +
				I18n.format(textFormatCode + "c" + "msg.cmdusage.line4");
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		if (args.length == 0) {
			sender.sendMessage(SpecialMessagesChat.getNormalMessage(getUsage(sender)));
		} else if (args.length == 1) {
			if(args[1].equalsIgnoreCase("toolpart") || args[1].equalsIgnoreCase("part"))
				dumpPart(sender);
			else if (args[1].equalsIgnoreCase("material") || args[1].equalsIgnoreCase("mat"))
				dumpMat(sender);
			else
				sender.sendMessage(SpecialMessagesChat.getNormalMessage(getUsage(sender)));
		} else {
			sender.sendMessage(SpecialMessagesChat.getNormalMessage(getUsage(sender)));
		}
	}

	private void dumpPart (ICommandSender sender) {
		try {
			if(!ToolPartStage.TOOL_PARTS_DUMP.exists())
				ToolPartStage.TOOL_PARTS_DUMP.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(ToolPartStage.TOOL_PARTS_DUMP));
			writer.write(I18n.format("txt.dump.tool_part.line1") + "\n"
					+ I18n.format("txt.dump.tool_part.line2") + "\n");
			for(IToolPart p : TinkerRegistry.getToolParts()) {
				if(p instanceof ToolPart) {
					ToolPart part = (ToolPart)p;
					String str = part.getUnlocalizedName();
					str = str.substring(str.lastIndexOf('.') + 1);
					writer.write(part.getItemStackDisplayName(new ItemStack(part)) + ":[" + str + "]\n");
				}
			}
			writer.close();
			sender.sendMessage(
					SpecialMessagesChat.getNormalMessage(textFormatCode + "f" + I18n.format("msg.dump_success.tool_part")).appendSibling(
					SpecialMessagesChat.getFileOpenText(textFormatCode + "6[ToolPartsDump]", ToolPartStage.TOOL_PARTS_DUMP.getPath())
					));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void dumpMat (ICommandSender sender) {
		try {
			if(!ToolPartStage.MATERIALS_DUMP.exists())
				ToolPartStage.MATERIALS_DUMP.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(ToolPartStage.MATERIALS_DUMP));
			writer.write(I18n.format("txt.dump.material.line1") + "\n"
					+ I18n.format("txt.dump.material.line2") + "\n");
			for(Material mat : TinkerRegistry.getAllMaterials()) {
				writer.write(mat.getLocalizedName() + ":[" + mat.getIdentifier() + "]\n");
			}
			writer.close();
			sender.sendMessage(
					SpecialMessagesChat.getNormalMessage(textFormatCode + "f" + I18n.format("msg.dump_success.material")).appendSibling(
					SpecialMessagesChat.getFileOpenText(textFormatCode + "6[MaterialsDump]", ToolPartStage.TOOL_PARTS_DUMP.getPath())
					));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
