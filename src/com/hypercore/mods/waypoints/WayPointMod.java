package com.hypercore.mods.waypoints;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Properties;

import org.gotti.wurmunlimited.modloader.ReflectionUtil;
import org.gotti.wurmunlimited.modloader.classhooks.HookManager;
import org.gotti.wurmunlimited.modloader.classhooks.InvocationHandlerFactory;
import org.gotti.wurmunlimited.modloader.interfaces.Configurable;
import org.gotti.wurmunlimited.modloader.interfaces.Initable;
import org.gotti.wurmunlimited.modloader.interfaces.PreInitable;
import org.gotti.wurmunlimited.modloader.interfaces.WurmClientMod;
import org.gotti.wurmunlimited.modsupport.console.ConsoleListener;
import org.gotti.wurmunlimited.modsupport.console.ModConsole;
import org.lwjgl.opengl.GL11;

import com.wurmonline.client.game.World;
import com.wurmonline.client.renderer.RenderVector;
import com.wurmonline.client.renderer.WorldRender;
import com.wurmonline.client.renderer.gui.HeadsUpDisplay;
import com.wurmonline.client.renderer.gui.MainMenu;
import com.wurmonline.client.renderer.gui.WindowWaypointManager;

import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.bytecode.Descriptor;

public class WayPointMod implements WurmClientMod, Initable, PreInitable, Configurable, ConsoleListener {
	
	//public static ArrayList<Vector3> coordinates = new ArrayList<Vector3>();
	
	public static World world;
	
	@Override
	public void preInit() {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void init() {
		
		Debug.Init();

		Debug.log("Registering HeadsUpDisplay.init Hook");
		
		//This taps into the HeadsUPDisplay "init" method
		HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.HeadsUpDisplay", "init",
				Descriptor.ofMethod(CtPrimitiveType.voidType, new CtClass[] {
						CtPrimitiveType.intType,
						CtPrimitiveType.intType
					}),
				new InvocationHandlerFactory() {

					@Override
					public InvocationHandler createInvocationHandler() {
						return new InvocationHandler() {

							@Override
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
								method.invoke(proxy, args);
								
								HeadsUpDisplay hud = (HeadsUpDisplay) proxy;
								MainMenu mainMenu = ReflectionUtil.getPrivateField(hud, ReflectionUtil.getField(hud.getClass(), "mainMenu"));
								mainMenu.registerComponent("Waypoint Manager", new WindowWaypointManager());

								return null;
							}
						};
					}
				});

		Debug.log("Registering HeadsUpDisplay.render Hook");
		HookManager.getInstance().registerHook("com.wurmonline.client.renderer.gui.HeadsUpDisplay", "render",
				Descriptor.ofMethod(CtPrimitiveType.voidType, new CtClass[] {
					CtPrimitiveType.floatType,
					CtPrimitiveType.booleanType,
					CtPrimitiveType.intType,
					CtPrimitiveType.intType
				}),
				new InvocationHandlerFactory() {
					@Override
					public InvocationHandler createInvocationHandler() {
						return new InvocationHandler() {

							@Override
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
								method.invoke(proxy, args);
								
								return null;
							}
						};
					}
				});
		
		
		Debug.log("Registering WorldRender.render Hook");
		HookManager.getInstance().registerHook("com.wurmonline.client.renderer.WorldRender", "render",
				Descriptor.ofMethod(CtPrimitiveType.voidType, new CtClass[] {
					CtPrimitiveType.intType,
					CtPrimitiveType.intType
				}),
				new InvocationHandlerFactory() {

					@Override
					public InvocationHandler createInvocationHandler() {
						return new InvocationHandler() {

							@Override
							public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
								method.invoke(proxy, args);
								WorldRender render = (WorldRender) proxy;
								
								@SuppressWarnings("unused")
								RenderVector rendervector = ReflectionUtil.getPrivateField(render, ReflectionUtil.getField(render.getClass(), "cameraOffset"));

								World world = ReflectionUtil.getPrivateField(render, ReflectionUtil.getField(render.getClass(), "world"));
								
								WayPointMod.world = world;
								
								//GL11.glTranslatef(camX, camY, camZ);
						        GL11.glRotatef(0, 1.0f, 0.0f, 0.0f);
						        GL11.glRotatef(0, 0.0f, 1.0f, 0.0f);
						        //GL11.glEnable(GL11.GL_DEPTH_TEST);
						        
						        //Here we are drawing the 3d shapes
								for(WayPoint point : WayPoint.waypoints) {
									
									float drawX = point.position.posx;
									float drawY = point.position.posy;
									float drawZ = point.position.posz;
									
									GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX+0, drawY+2, drawZ+0);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX-1, drawY+0, drawZ+1);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX+1, drawY+0, drawZ+1);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX+0f, drawY+0f, drawZ-1.4f);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX+0, drawY+2, drawZ+0);
										GL11.glColor3f(point.r, point.g, point.b); GL11.glVertex3f(drawX-1, drawY+0, drawZ+1);
									GL11.glEnd();
									
								}
								
								//GL11.glDisable(GL11.GL_DEPTH_TEST);
								GL11.glFlush();
								
								return null;
							}
						};
					}
				});
		ModConsole.addConsoleListener(this);
		
	}

	@Override
	public boolean handleInput(String cmd, Boolean arg2) {
		if(cmd.equals("wp")) {
			Debug.log("Toggeling Waypoint Manager Window");
			Debug.log("There was an error processing your request.");
			return true;
		}
		return false;
	}

	@Override
	public void configure(Properties arg0) {
		// TODO Auto-generated method stub
		
	}

}
