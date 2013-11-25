package com.prodyna.pac.conference.beans;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.icefaces.ace.component.fileentry.FileEntry;
import org.icefaces.ace.component.fileentry.FileEntryEvent;
import org.icefaces.ace.component.fileentry.FileEntryResults;

import com.prodyna.pac.conference.speaker.model.Speaker;
import com.prodyna.pac.conference.speaker.service.SpeakerReferencedException;
import com.prodyna.pac.conference.speaker.service.SpeakerService;
import com.prodyna.pac.conference.talk.model.Talk;
import com.prodyna.pac.conference.talk.service.TalkService;
import com.prodyna.pac.conference.web.constants.ViewConstants;

@ManagedBean(name = "speakerDetails")
@ViewScoped
public class SpeakerDetails extends AbstractEditEntityMaskBean {
	private static final long serialVersionUID = 1L;

	@Inject
	SpeakerService speakerService;

	@Inject
	TalkService talkService;

	private Speaker speaker;

	private List<String> fileData;

	public Speaker getSpeaker() throws Exception
	{
		if (speaker == null) {
			if (isNewMode()) {
				initNewSpeaker();
			} else {
				speaker = speakerService.getSpeakerById(getId());
			}
		}
		return speaker;
	}

	public void setSpeaker(Speaker speaker)
	{
		this.speaker = speaker;
	}

	public List<Talk> getTalks() throws Exception
	{
		return talkService.getTalksBySpeaker(getSpeaker());
	}

	public void initNewSpeaker()
	{
		speaker = new Speaker();
	}

	@Override
	public String cancelEditing()
	{
		initNewSpeaker();

		return ViewConstants.VIEW_SPEAKER_LIST;
	}

	public String createSpeaker() throws Exception
	{
		String outcome = ViewConstants.VIEW_SPEAKER_EDIT;

		try {
			speakerService.createSpeaker(speaker);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Saved!", "Speaker saved"));

			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Saving unsuccessful");
			facesContext.addMessage(null, m);
		}

		return outcome;
	}

	public String updateSpeaker() throws Exception
	{
		String outcome = ViewConstants.VIEW_SPEAKER_EDIT;
		try {
			speakerService.updateSpeaker(speaker);
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_INFO, "Update!", "Speaker updated"));
			outcome = ViewConstants.VIEW_SPEAKER_LIST;
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					errorMessage, "Updating speaker unsuccessful");
			facesContext.addMessage(null, m);
		}
		return outcome;
	}

	public String getProfileImagePath() throws Exception
	{
		return getSpeaker().getProfileImage();
	}

	public boolean hasProfileImage() throws Exception
	{
		boolean result = false;

		if (getSpeaker() != null && getSpeaker().getProfileImage() != null) {
			String realPath = facesContext.getExternalContext().getRealPath(
					"/" + getSpeaker().getProfileImage());

			File file = new File(realPath);

			result = file.exists();
		}

		return result;
	}

	public void uploadListener(FileEntryEvent e)
	{
		FileEntry fe = (FileEntry) e.getComponent();
		FileEntryResults results = fe.getResults();
		File parent = null;

		fileData = new ArrayList<String>();

		// get data About File

		for (FileEntryResults.FileInfo i : results.getFiles()) {

			if (i.isSaved()) {
				String webAppRoot = facesContext.getExternalContext()
						.getRealPath("/");

				String targetFolder = "profileimages/" + UUID.randomUUID()
						+ "/";

				File f = new File(webAppRoot, targetFolder);
				f.mkdirs();
				File file = i.getFile();
				File dest = new File(f, file.getName());
				file.renameTo(dest);

				speaker.setProfileImage(targetFolder + file.getName());

			} else {
				fileData.add("File was not saved because: "
						+ i.getStatus()
								.getFacesMessage(
										FacesContext.getCurrentInstance(), fe,
										i).getSummary());
			}
		}
	}

	public List<String> getFileData()
	{
		return fileData;
	}

	public void deleteSpeaker(Speaker speaker) throws Exception
	{
		try {
			speakerService.deleteSpeaker(speaker);
		} catch (SpeakerReferencedException e) {
			facesContext
					.addMessage(
							null,
							new FacesMessage(
									FacesMessage.SEVERITY_INFO,
									"Speaker '"
											+ speaker.getName()
											+ "' cannot be deleted because it is still in use.",
									null));

		}
	}

}