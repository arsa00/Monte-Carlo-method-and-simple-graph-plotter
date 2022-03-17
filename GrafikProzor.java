package zadatak;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class GrafikProzor extends Frame
{
	private static final int Yoffset = 50;
	private static final int Xoffset = 50;
	
	private boolean grid = true;
	private boolean thLine = false;
	private double zoom = 0;
	private boolean txt = true;
	private double XparamsOffset;
	private double YparamsOffset;
	
	private ArrayList<Integer> Xparams;
	private ArrayList<Double> Yparams;
	
	private static ArrayList<Integer> Xparams2 = new ArrayList<Integer>(Arrays.asList(100, 20, 10));
	private static ArrayList<Double> Yparams2 = new ArrayList<Double>(Arrays.asList(0.35, 0.28, 0.32));
	
	public GrafikProzor()
	{	
		this(Xparams2, Yparams2);
	}
	
	public GrafikProzor(ArrayList<Integer> Xparams, ArrayList<Double> Yparams)
	{
		this.Xparams = Xparams;
		this.Yparams = Yparams;
		
		Yparams2.clear();
		for(int i = 0; i < Yparams.size(); i++)
			Yparams2.add(Double.parseDouble(String.format("%.3f", Yparams.get(i))));
		
		this.Yparams = Yparams2; 
		
		setBounds(200, 10, 1200, 800);
		setTitle("Grafik");
		setResizable(false);
		
		
		if(Xparams.size() != Yparams.size() || Xparams.size() == 0) 
		{
			dispose();
		}
		
		Button zoomBtn = new Button("Zoom");
		Checkbox cb = new Checkbox("Grid lines", true);
		Checkbox cb2 = new Checkbox("Theoretical line", false);
		Checkbox cb3 = new Checkbox("Text", true);
		
		cb.addItemListener((ie) -> {
			if(ie.getStateChange() == ItemEvent.SELECTED)
			{
				grid = true;
			}
			else
			{
				grid = false;
			}
			
			repaint();
		});
		
		cb2.addItemListener((ie) -> {
			if(ie.getStateChange() == ItemEvent.SELECTED)
			{
				thLine = true;
			}
			else
			{
				thLine = false;
			}
			
			repaint();
		});
		
		cb3.addItemListener((ie) -> {
			if(ie.getStateChange() == ItemEvent.SELECTED)
			{
				txt = true;
			}
			else
			{
				txt = false;
			}
			
			repaint();
		});
		
		zoomBtn.addActionListener((ae) ->{
			if(zoom == 0 && Yparams.size() > 1)	
			{
				zoom = Collections.min(Yparams) > 0.25 ? 0.25 : Collections.min(Yparams);
			}
			else
				zoom = 0;
			
			repaint();
		});
		
		Panel pBtn = new Panel();
		pBtn.add(zoomBtn);
		
		Panel p = new Panel(new GridLayout(0, 1, 0, 5));
		
		p.add(cb);
		p.add(cb2);
		p.add(cb3);
		p.add(pBtn);
		
		add(p, BorderLayout.EAST);
		
		addWindowListener(new WindowAdapter()
		{
			@Override
			public void windowClosing(WindowEvent e)
			{
				dispose();
			}
		});
		
		setVisible(true);
	}
	
	@Override
	public void paint(Graphics g)
	{
		Y_osa(g);
		X_osa(g);
		XParamDraw(g);
		YParamDraw(g);
		plot(g);
		super.paint(g);
	}
	
	private void Y_osa(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(Xoffset, Yoffset, Xoffset, this.getHeight() - Yoffset);
		g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2.setColor(Color.RED);
		g2.drawString("Pn", Xoffset/3, Yoffset);
		g2.setColor(Color.BLACK);
	}
	
	private void X_osa(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		g2.setStroke(new BasicStroke(3));
		g2.drawLine(Xoffset, this.getHeight() - Yoffset, this.getWidth() - Xoffset, this.getHeight() - Yoffset);
		g2.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g2.setColor(Color.RED);
		g2.drawString("n", this.getWidth() - 2*Xoffset - 15, this.getHeight() - Yoffset/2);
		g2.setColor(Color.BLACK);
	}
	
	private void XParamDraw(Graphics g)
	{
		int xPos = Xoffset, yPos = this.getHeight() - Yoffset/2;
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		XparamsOffset = (double)(this.getWidth() - 4*Xoffset)/Collections.max(Xparams);
		
		for(int i = 0; i < Xparams.size(); i++)
		{
			g.drawRect(xPos + (int)(Xparams.get(i)*XparamsOffset), this.getHeight() - Yoffset-5, 2, 10);
			if(txt)
				g.drawString(new StringBuilder().append(Xparams.get(i)).toString(), xPos + (int)(Xparams.get(i)*XparamsOffset), yPos);
		}
		
		if(!txt)
		{
			int dek = 10;
				
			while((double)(Collections.max(Xparams)/(dek*10)) >= 1)
			{
				dek *= 10;
			}
			if((double)Collections.max(Xparams)/dek < 2)
				dek /= 10;
			for(int k = dek; k <= Collections.max(Xparams); k += dek)
			{
				g.drawString(new StringBuilder().append(k).toString(), xPos + (int)(k*XparamsOffset), yPos);
			}
		}
	}
	
	private void YParamDraw(Graphics g)
	{
		int xPos = Xoffset, yPos = this.getHeight() - Yoffset/2;
		
		YparamsOffset = (double)((this.getHeight() - 3*Yoffset)/(Collections.max(Yparams) - (zoom)));
		
		for(int i = 0; i < Yparams.size(); i++)
		{
			g.drawRect(Xoffset - 4, yPos - (int)((Yparams.get(i) - zoom) * YparamsOffset) - Yoffset, 8, 1);
			// Ypawams2 zaokruzen na 3 decimale
			if(txt)
				g.drawString(new StringBuilder().append(Yparams2.get(i)).toString(), 12, yPos - (int)((Yparams2.get(i) - zoom) * YparamsOffset) - Yoffset);
				
		}
		if(!txt)
			g.drawString("0.25", 12, yPos - (int)((0.25 - zoom) * YparamsOffset) - Yoffset);
	}
	
	private void plot(Graphics g)
	{
		Graphics2D g2 = (Graphics2D)g;
		Ellipse2D.Double krug; 
		int xPos = Xoffset, yPos = this.getHeight() - Yoffset/2;
		
		// draw dots and "grid" lines
		for(int i = 0; i < Xparams.size(); i++)
		{
			if(grid)
			{
				g2.setStroke(new BasicStroke(1));
				// y-osa grid
				g2.drawLine((int)(xPos + Xparams.get(i)*XparamsOffset), yPos - Yoffset/2, (int)(xPos + Xparams.get(i)*XparamsOffset), (int)(yPos - (Yparams.get(i) - zoom)*YparamsOffset - Yoffset));
				// x-osa grid
				g2.drawLine(xPos, (int)(yPos - (Yparams.get(i) - zoom)*YparamsOffset - Yoffset), (int)(xPos + Xparams.get(i)*XparamsOffset), (int)(yPos - (Yparams.get(i) - zoom)*YparamsOffset - Yoffset));
			}
			
			krug = new Ellipse2D.Double((int)(xPos + Xparams.get(i)*XparamsOffset) - 4, (int)(yPos - (Yparams.get(i) - zoom)*YparamsOffset - Yoffset) - 4, 8, 8);
			g2.setColor(Color.RED);
			g2.fill(krug);
			g2.setColor(Color.BLACK);
			
			
			//g2.drawLine(xPos, yPos, (int)(xPos + Xparams.get(i)*XparamsOffset), yPos);
			//g.drawRoundRect((int)(xPos + Xparams.get(i)*XparamsOffset), (int)(yPos - Yparams.get(i)*YparamsOffset), 5, 5, 5, 5);
		}
		
		//draw theoretical probability line
		if(thLine)
		{
			g2.setColor(Color.RED);
			g2.setStroke(new BasicStroke(3));
			//g2.drawLine(xPos, (int)(yPos - 0.25*YparamsOffset)+1, this.getWidth() - Xoffset, (int)(yPos - 0.25*YparamsOffset)+1);
			g2.drawLine(xPos, (int)(yPos - (0.25 - zoom)*YparamsOffset - Yoffset), this.getWidth() - Xoffset, (int)(yPos - (0.25 - zoom)*YparamsOffset - Yoffset));
			g.drawString("0.25", 12, yPos - (int)((0.25 - zoom) * YparamsOffset) - Yoffset);
			g2.setColor(Color.BLACK);
		}
		
	}
	
	public static void main(String[] args)
	{
		new GrafikProzor();
	}
}

