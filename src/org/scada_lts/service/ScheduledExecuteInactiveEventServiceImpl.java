package org.scada_lts.service;

import com.serotonin.mango.rt.event.AlarmLevels;
import com.serotonin.mango.rt.event.EventInstance;
import com.serotonin.mango.rt.event.ScheduledEvent;
import com.serotonin.mango.vo.event.EventHandlerVO;
import com.serotonin.mango.vo.mailingList.MailingList;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.scada_lts.dao.event.ScheduledExecuteInactiveEventDAO;
import org.scada_lts.mango.service.MailingListService;

import java.util.List;

class ScheduledExecuteInactiveEventServiceImpl implements ScheduledExecuteInactiveEventService {

    private static final Log log = LogFactory.getLog(ScheduledExecuteInactiveEventServiceImpl.class);
    private final ScheduledExecuteInactiveEventDAO scheduledEventDAO;
    private final MailingListService mailingListService;
    private static class LazyHolder {
        public static final ScheduledExecuteInactiveEventService INSTANCE =
                new ScheduledExecuteInactiveEventServiceImpl(ScheduledExecuteInactiveEventDAO.getInstance(),
                        new MailingListService());
    }

    static ScheduledExecuteInactiveEventService getInstance() {
        return LazyHolder.INSTANCE;
    }

    ScheduledExecuteInactiveEventServiceImpl(ScheduledExecuteInactiveEventDAO scheduledEventDAO,
                                                     MailingListService mailingListService) {
        this.scheduledEventDAO = scheduledEventDAO;
        this.mailingListService = mailingListService;
    }

    @Override
    public boolean scheduleEvent(EventHandlerVO eventHandler, EventInstance event) {
        if(event.getAlarmLevel() == AlarmLevels.NONE) {
            log.warn("Event with alarm level NONE: event type:" + event.getEventType());
            return false;
        }
        if(eventHandler.getHandlerType() != EventHandlerVO.TYPE_SMS &&
                eventHandler.getHandlerType() != EventHandlerVO.TYPE_EMAIL) {
            log.warn("Event handler type not supported:" + eventHandler.getClass().getSimpleName());
            return false;
        }
        return schedule(eventHandler, event);
    }

    @Override
    public void scheduleEventFail(EventHandlerVO eventHandler, EventInstance event) {
        if(event.getAlarmLevel() == AlarmLevels.NONE) {
            log.warn("Event with alarm level NONE: event type:" + event.getEventType());
            return;
        }
        if(eventHandler.getHandlerType() != EventHandlerVO.TYPE_SMS &&
                eventHandler.getHandlerType() != EventHandlerVO.TYPE_EMAIL) {
            log.warn("Event handler type not supported:" + eventHandler.getClass().getSimpleName());
            return;
        }
        scheduleFail(eventHandler, event);
    }

    @Override
    public void unscheduleEvent(ScheduledEvent event, CommunicationChannel channel) {
        unscheduleEvent(event.getEventHandler(), event.getEvent(), channel);
    }

    private void unscheduleEvent(EventHandlerVO eventHandler,
                                 EventInstance event,
                                 CommunicationChannel communicationChannel) {
        if(communicationChannel.getType().getEventHandlerType() == eventHandler.getHandlerType()) {
            ScheduledExecuteInactiveEventInstance inactiveEventInstance =
                    new ScheduledExecuteInactiveEventInstance(eventHandler, event, communicationChannel.getData());
            try {
                scheduledEventDAO.delete(inactiveEventInstance.getKey());
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }
    }

    private boolean schedule(EventHandlerVO eventHandler, EventInstance event) {
        List<MailingList> mailingLists = mailingListService.convertToMailingLists(eventHandler.getActiveRecipients());

        boolean scheduled = false;
        for (MailingList mailingList : mailingLists) {
            if (mailingList.isCollectInactiveEmails()) {
                ScheduledExecuteInactiveEventInstance inactiveEventInstance =
                        new ScheduledExecuteInactiveEventInstance(eventHandler, event, mailingList);
                if (!inactiveEventInstance.isActive()) {
                    try {
                        scheduledEventDAO.insert(inactiveEventInstance.getKey());
                        scheduled = true;
                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }
            }
        }
        return scheduled;
    }

    private void scheduleFail(EventHandlerVO eventHandler, EventInstance event) {
        List<MailingList> mailingLists = mailingListService.convertToMailingLists(eventHandler.getActiveRecipients());

        for (MailingList mailingList : mailingLists) {
            if (mailingList.isCollectInactiveEmails()) {
                ScheduledExecuteInactiveEventInstance inactiveEventInstance =
                        new ScheduledExecuteInactiveEventInstance(eventHandler, event, mailingList);
                try {
                    scheduledEventDAO.insert(inactiveEventInstance.getKey());
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }
            }
        }
    }
}
